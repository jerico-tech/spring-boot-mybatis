package com.jerico.springboot.mybatis.controller;

import com.jerico.springboot.mybatis.entity.UserEntity;
import com.jerico.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserEntity saveUser(@RequestBody UserEntity userEntity) {
        userService.saveUser(userEntity);
        return userEntity;
    }
}
