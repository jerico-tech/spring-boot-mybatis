package com.jerico.springboot.mybatis.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 借助hutool封装一个生成雪花算法分布式id的bean。
 */
@Slf4j
@Component
public class IdGeneratorSnowflake {
    /**
     * 使用雪花算法生成分布式id，为了保证不同数据中心和节点生成的id不重复,要做到以下两点：
     * 1. 要保证不同数据中心和节点时钟一致;
     * 2. 保证workId和dataCenterId不同，由于同一个包在不同数据中心和节点部署，为了保证两个值不同，需要按数据中心和节点进行分配和配置（环境变量或者配置中心）；
     */
    private long workerId = 0;
    private long datacenterId = 1;
    // 这里相当于一直是以workId=1来getSnowflake
    private Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            System.out.println("当前机器的workerId:{}" + workerId);
        } catch (Exception e) {
            System.out.println("当前机器的workerId获取失败" + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
            System.out.println("当前机器 workId:{}" + workerId);
        }
    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId) {
        snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
}
