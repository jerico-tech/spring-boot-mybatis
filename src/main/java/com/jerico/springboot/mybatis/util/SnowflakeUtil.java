package com.jerico.springboot.mybatis.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 借助hutool封装一个生成雪花算法分布式id的静态工具类。
 */
@Component
public class SnowflakeUtil {
    /**
     * 使用雪花算法生成分布式id，为了保证不同数据中心和节点生成的id不重复,要做到以下两点：
     * 1. 要保证不同数据中心和节点时钟一致;
     * 2. 保证workId和dataCenterId不同，由于同一个包在不同数据中心和节点部署，为了保证两个值不同，需要按数据中心和节点进行分配和配置（环境变量或者配置中心）；
     */
    private static long workerId = 0;
    private static long datacenterId = 1;
    private static Snowflake snowflake = null;

    @PostConstruct
    public void init() {
        snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    }

    public synchronized static long snowflakeId() {
        return snowflake.nextId();
    }

    public synchronized static long snowflakeId(long workerId, long datacenterId) {
        snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
}
