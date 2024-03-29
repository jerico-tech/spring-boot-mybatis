package com.jerico.springboot.mybatis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jerico.commons.json.JsonUtils;
import com.jerico.springboot.mybatis.entity.UserEntity;
import com.jerico.springboot.mybatis.service.UserService;
import com.jerico.springboot.mybatis.util.ResponseDTO;
import com.jerico.springboot.mybatis.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户单个与批量接口，演示Controller代码如何写，并演示ResponseEntity的一种使用方式。
 */
@Tag(name = "用户CRUD接口", description = "对用户进行相关操作接口")
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
     * 根据查询参数id返回用户信息，演示responseEntity
     *
     * @param id 用户id
     * @return 用户对象的封装对象
     */
    @Operation(summary = "find user", description = "find user by user id")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/id")
    public ResponseEntity<Object> getUserById(@Parameter(in = ParameterIn.QUERY) @RequestParam int id) throws Exception {
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
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") int id) throws JsonProcessingException {
        UserEntity user = userService.getUser(id);
        System.out.println("user: " + JsonUtils.objToJson(user));
        return user;
    }

    @GetMapping
    public List<UserEntity> listUsers() {
        return userService.listUsers();
    }


    /**
     * 添加用户
     *
     * @param userEntity 用户对象
     * @return 用户对象（含主键）
     */
    @PostMapping

    public UserEntity saveUser(@RequestBody UserEntity userEntity) {
        userService.saveUser(userEntity);
        return userEntity;
    }

    /**
     * @param userEntity
     * @return
     */
    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable int id, @RequestBody UserEntity userEntity) {
        userEntity.setId(id);
        return userService.updateUser(userEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PostMapping("/batch")
    public List<UserEntity> batchSaveUser(@RequestBody List<UserEntity> userList) {
        return userService.batchSaveUser(userList);
    }

    @PutMapping
    public List<UserEntity> batchUpdateUser(@RequestBody List<UserEntity> userEntityList) {
        userService.batchUpdateUser(userEntityList);
        return userEntityList;
    }

    /**
     * 批量删除用户。非200的成功消息，单独封装返回，断点跟踪发现封装后不会进入ResponseAdvice的方法.
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Object> batchRemoveUser(@RequestParam List<Integer> ids) {
        userService.batchRemoveUser(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
