package com.jerico.springboot.mybatis.dao;

import com.jerico.springboot.mybatis.entity.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface UserDAO {
    int insertUser(UserEntity userEntity);

    UserEntity getUser(int id);

    UserEntity getUserByName(String name);

    int updateUser(UserEntity userEntity);

    int deleteUser(int id);

    List<UserEntity> listUsers();

    int batchInsertUser(List<User> userList);

    int batchUpdateUser(List<User> userList);

    int batchDeleteUser(List<User> userList);

}
