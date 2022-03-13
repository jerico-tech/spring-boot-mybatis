package com.jerico.springboot.mybatis.service;

import com.jerico.springboot.mybatis.entity.UserEntity;

public interface UserService {
    int saveUser(UserEntity userEntity);

    UserEntity getUser(int id);
}
