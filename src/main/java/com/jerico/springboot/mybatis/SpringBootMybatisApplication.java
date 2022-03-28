package com.jerico.springboot.mybatis;

import com.jerico.springboot.mybatis.config.IdGeneratorSnowflake;
import com.jerico.springboot.mybatis.util.SnowflakeIdWorker;
import com.jerico.springboot.mybatis.util.SnowflakeSingle;
import com.jerico.springboot.mybatis.util.SnowflakeUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.jerico.springboot.mybatis.dao")
public class SpringBootMybatisApplication {

    /**
     * 注入雪花算法bean
     */
    @Autowired
    private IdGeneratorSnowflake idGeneratorSnowflake;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
        System.out.println("方式一:借助hutool以静态工具类方式生成雪花算法分布式id：" + SnowflakeUtil.snowflakeId());
        System.out.println("方式二:借助hutool以单例方式生成雪花算法分布式id：" + SnowflakeSingle.getInstance().snowflakeId());
        // 方式三是非静态方法，所以无法在静态方法中使用。
        System.out.println("方式四:借助common lang3以静态工具类方式生成雪花算法分布式id：" + SnowflakeIdWorker.generateId());
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("方式一:借助hutool以静态工具类方式生成雪花算法分布式id：" + SnowflakeUtil.snowflakeId());
        System.out.println("方式二:借助hutool以单例方式生成雪花算法分布式id：" + SnowflakeSingle.getInstance().snowflakeId());
        System.out.println("方式三:借助hutool以bean注入方式生成雪花算法分布式id：" + idGeneratorSnowflake.snowflakeId());
        System.out.println("方式四:借助common lang3以静态工具类方式生成雪花算法分布式id：" + SnowflakeIdWorker.generateId());
        return String.format("Hello %s!", name);
    }

    @GetMapping("/exception")
    public int exception() {
        int i = 9 / 0;
        return i;
//        throw new ArithmeticException(); // 如果new ArithmeticException()也不会进入beforeBodyWrite方法内部
    }

    @GetMapping("/error")
    public int error() {
        throw new ArithmeticException();
    }

    @GetMapping("/not-found")
    public void notFound() throws NotFoundException {
        throw new NotFoundException("x");
    }
}
