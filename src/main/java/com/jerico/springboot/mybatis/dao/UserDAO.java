package com.jerico.springboot.mybatis.dao;

import com.jerico.springboot.mybatis.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    int insertUser(UserEntity userEntity);

    UserEntity getUser(int id);

    List<UserEntity> listUsers();

    int updateUser(UserEntity userEntity);

    int deleteUser(int id);
}
