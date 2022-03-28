package com.jerico.springboot.mybatis.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 借助commons lang3实现雪花算法生成分布式id。
 * Twitter_Snowflake<br>
 * https://blog.csdn.net/u012488504/article/details/82194495
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class SnowflakeIdWorker {
    /**
     * 开始时间戳（2022-01-01）
     */
    private final long startTimestamp = 1489111610226L;

    /**
     * 使用雪花算法生成分布式id，为了保证不同数据中心和节点生成的id不重复,要做到以下两点：
     * 1. 要保证不同数据中心和节点时钟一致;
     * 2. 保证workId和dataCenterId不同，由于同一个包在不同数据中心和节点部署，为了保证两个值不同，需要按数据中心和节点进行分配和配置（环境变量或者配置中心）；
     */
    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心id所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31（这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数）
     */
    private final long maxWorkId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据中心标识id，结果是31
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器id向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据中心id向左移17位（12+5）
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳向左移22位（12+5+5）
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdShift;

    /**
     * 生成序列的掩码，这里为4095
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID（0~31）
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列（0~4095）
     */
    private long sequence = 0L;

    /**
     * 上次生成Id的时间戳
     */
    private long lastTimestamp = -1L;

    private static SnowflakeIdWorker idWorker;

    static {
        long workerId = getWorkId();
        long dataCenterId = getDataCenterId();
        System.out.println("workerId = " + workerId + "dataCenterId = " + dataCenterId);
        idWorker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    /**
     * 构造方法
     *
     * @param workId       工作id（0~31）
     * @param dataCenterId 数据中心id （0~31）
     */
    public SnowflakeIdWorker(long workId, long dataCenterId) {
        if (workId > maxWorkId || workId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获得下一个ID（该方法是线程安全的）
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，这个时间应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else { // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        // 上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位ID
        return ((timestamp - startTimestamp) << timestampLeftShift | (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 当前时间错
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间（毫秒）
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 把ip地址转换为ascii，把ascii相加，对32取余，个人理解无法保证workid的唯一性。
     * @return
     */
    private static long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            System.out.println("hostAddress: " + hostAddress);
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtils.nextLong(0, 32);
        }
    }

    private static long getDataCenterId() {
        System.out.println("hostName: " + SystemUtils.getHostName());
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % 32);
    }

    /**
     * 静态工具类
     *
     * @return 雪花算法id
     */
    public static Long generateId() {
        return idWorker.nextId();
    }
}
