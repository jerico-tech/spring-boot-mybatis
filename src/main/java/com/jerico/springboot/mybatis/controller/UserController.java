package com.jerico.springboot.mybatis.controller;

import com.jerico.springboot.mybatis.entity.UserEntity;
import com.jerico.springboot.mybatis.exception.ParamErrorException;
import com.jerico.springboot.mybatis.service.UserService;
import com.jerico.springboot.mybatis.util.ResponseCodeEnum;
import com.jerico.springboot.mybatis.util.ResponseDTO;
import com.jerico.springboot.mybatis.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户单个与批量接口，演示Controller代码如何写，并演示ResponseEntity的一种使用方式。
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/error")
    public int error() throws Exception {
        throw new Exception();
    }

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户对象
     */
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    /**
     * 根据查询参数id返回用户信息，演示responseEntity
     * @param id 用户id
     * @return 用户对象的封装对象
     */
    @GetMapping
    public ResponseEntity<Object> getUserById(@RequestParam int id) throws Exception {
        return ResponseEntity.ok(userService.getUser(id));
//        return ResponseUtil.response("Success", HttpStatus.CREATED, userService.getUser(id));
    }

    /**
     * 根据名称查询用户
     */
    @GetMapping("/name")
    public ResponseDTO<UserEntity> getUserByName(@RequestParam String name) {
        return ResponseUtil.success(userService.getUserByName(name));
    }

    /**
     * 添加用户
     * @param userEntity 用户对象
     * @return 用户对象（含主键）
     */
    @PostMapping
    public UserEntity saveUser(@RequestBody UserEntity userEntity) {
        if (userEntity.getName() == null) {
            throw new ParamErrorException(ResponseCodeEnum.NAME_NOT_NULL.getCode(), ResponseCodeEnum.NAME_NOT_NULL.getMessage());
        }
        userService.saveUser(userEntity);
        return userEntity;
    }

    /**
     *
     * @param userEntity
     * @return
     */
    public UserEntity updateUser(@RequestBody UserEntity userEntity) {
        return new UserEntity();
    }
}
