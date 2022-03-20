package com.jerico.springboot.mybatis;

import org.apache.ibatis.javassist.NotFoundException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.jerico.springboot.mybatis.dao")
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
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
