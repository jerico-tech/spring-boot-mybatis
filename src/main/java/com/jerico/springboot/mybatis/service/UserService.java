package com.jerico.springboot.mybatis.service;

import com.jerico.springboot.mybatis.entity.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    int saveUser(UserEntity userEntity);

    UserEntity getUser(int id);

    UserEntity getUserByName(String name);

    List<UserEntity> listUsers();

    UserEntity updateUser(UserEntity userEntity);

    void deleteUser(int id);

    List<UserEntity> batchSaveUser(List<UserEntity> userEntityList);

    List<UserEntity> batchUpdateUser(List<UserEntity> userEntityList);

    void batchRemoveUser(List<Integer> userEntityList);
}
