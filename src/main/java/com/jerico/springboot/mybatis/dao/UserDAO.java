package com.jerico.springboot.mybatis.dao;

import com.jerico.springboot.mybatis.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    int insertUser(UserEntity userEntity);

    UserEntity getUser(int id);

    UserEntity getUserByName(String name);

    int updateUser(UserEntity userEntity);

    int deleteUser(int id);

    List<UserEntity> listUsers();

    int batchInsertUser(List<UserEntity> userList);

    int batchUpdateUser(List<UserEntity> userList);

    int batchDeleteUser(List<Integer> userList);

}
