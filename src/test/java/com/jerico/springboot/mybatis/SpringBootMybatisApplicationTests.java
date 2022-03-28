package com.jerico.springboot.mybatis;

import cn.hutool.core.net.NetUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootTest
class SpringBootMybatisApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLocalhost() {
        String localhost =  NetUtil.getLocalhostStr();
        System.out.println(localhost);
        Long workerId = NetUtil.ipv4ToLong(localhost);
        System.out.println(workerId);
        String hostAddress = null;
        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("hostAddress: " + hostAddress);
        int[] ints = StringUtils.toCodePoints(hostAddress);
        int sums = 0;
        for (int b : ints) {
            System.out.println(b);
            sums += b;
        }
        System.out.println((long) (sums % 32));
    }

    @Test
    void test() {
        String hostAddress = null;
        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("hostAddress: " + hostAddress);
        int[] ints = StringUtils.toCodePoints(hostAddress);
        System.out.println(Arrays.toString(ints));
//        int sums = 0;
//        for (int b : ints) {
//            System.out.println(b);
//            sums += b;
//        }
//        System.out.println((long) (sums % 32));
    }

}
