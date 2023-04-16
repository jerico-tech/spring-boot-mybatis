package com.jerico.springboot.mybatis;

import com.jerico.springboot.mybatis.config.IdGeneratorSnowflake;
import com.jerico.springboot.mybatis.util.SnowflakeIdWorker;
import com.jerico.springboot.mybatis.util.SnowflakeSingle;
import com.jerico.springboot.mybatis.util.SnowflakeUtil;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.apache.ibatis.javassist.NotFoundException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairu
 * @date 2023-4-16 21:20
 */
@SpringBootApplication
@RestController
@MapperScan("com.jerico.springboot.mybatis.dao")
@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot构建的基础工程的 REST API Documentation",
        description = "使用Spring Boot构建的基础工程的 REST API Documentation",
        version = "v1.0",
        contact = @Contact(
            name = "jerico",
            email = "hairui1986sd@163.com",
            url = "https://github.com/jerico-tech"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://github.com/jerico-tech"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "Spring Boot User Management Documentation",
        url = "https://github.com/jerico-tech"
    )
)

//为Swagger整合JWT支持，项目没有令牌模块下面这个注解可以不写
@SecurityScheme(
    name = "Token",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SpringBootMybatisApplication {

    /**
     * 注入雪花算法bean
     */
    @Autowired
    private IdGeneratorSnowflake idGeneratorSnowflake;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
        System.out.println(
            "方式一:借助hutool以静态工具类方式生成雪花算法分布式id：" + SnowflakeUtil.snowflakeId());
        System.out.println(
            "方式二:借助hutool以单例方式生成雪花算法分布式id：" + SnowflakeSingle.getInstance()
                .snowflakeId());
        // 方式三是非静态方法，所以无法在静态方法中使用。
        System.out.println("方式四:借助common lang3以静态工具类方式生成雪花算法分布式id："
            + SnowflakeIdWorker.generateId());
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println(
            "方式一:借助hutool以静态工具类方式生成雪花算法分布式id：" + SnowflakeUtil.snowflakeId());
        System.out.println(
            "方式二:借助hutool以单例方式生成雪花算法分布式id：" + SnowflakeSingle.getInstance()
                .snowflakeId());
        System.out.println("方式三:借助hutool以bean注入方式生成雪花算法分布式id："
            + idGeneratorSnowflake.snowflakeId());
        System.out.println("方式四:借助common lang3以静态工具类方式生成雪花算法分布式id："
            + SnowflakeIdWorker.generateId());
        return String.format("Hello %s!", name);
    }

    @GetMapping("/exception")
    public int exception() {
        int i = 9 / 0;
        return i;
//        throw new ArithmeticException(); // 如果new ArithmeticException()也不会进入beforeBodyWrite方法内部
    }

    @GetMapping("/error")
    public void error() {
        System.out.println("error");
    }

    @GetMapping("/not-found")
    public void notFound() throws NotFoundException {
        throw new NotFoundException("x");
    }

    private String hello() {
        return new String();
    }

    /**
     * @return void
     * @author hairu
     * @date 2023-4-16 20:34
     */
    private void word() {
        System.out.println();
    }

}
