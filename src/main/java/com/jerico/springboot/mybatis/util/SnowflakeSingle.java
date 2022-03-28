package com.jerico.springboot.mybatis.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
/**
 * 借助HuTool封装一个生成雪花算法分布式id的单例类。
 */
public class SnowflakeSingle {
    private final static Logger log = LoggerFactory.getLogger(SnowflakeUtil.class);
    private static long workerId = 0;

    private volatile static Snowflake snowflake = null;
    private volatile static SnowflakeSingle snowflakeSingle = null;

    /**
     * 单例
     */
    private SnowflakeSingle() {

    }

    public static SnowflakeSingle getInstance() {
        if (null == snowflakeSingle) {
            synchronized (SnowflakeSingle.class) {
                if (null == snowflakeSingle) {
                    snowflakeSingle = new SnowflakeSingle();
                }
            }
        }
        return snowflakeSingle;
    }

    /**
     *  获取ID
     * @return
     */
    public long snowflakeId() {
        if (null == snowflake) {
            snowflake = getSnowflake();
        }
        return snowflake.nextId();
    }

    /**
     * 获取ID(自定义工作机器ID)
     * @param workerId 终端ID
     * @param dataCenterId 数据中心ID
     * @return
     */
    public long snowflakeId(long workerId, long dataCenterId) {
        if (null == snowflake) {
            snowflake = getSnowflake(workerId, dataCenterId);
        }
        return snowflake.nextId();
    }

    /**
     *  Snowflake带参对象获取
     * @param workerId
     * @param dataCenterId
     * @return
     */
    private synchronized Snowflake getSnowflake(long workerId, long dataCenterId) {
        snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
        return snowflake;
    }

    /**
     * Snowflake无参对象获取,使用默认值创建snowflake，这是比较危险的。
     * @return
     */
    private synchronized Snowflake getSnowflake() {
        snowflake = new Snowflake();
        return snowflake;
    }

    @PostConstruct
    public void init() {
        workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        log.info("当前机器的workId: {}", workerId);
    }
}
