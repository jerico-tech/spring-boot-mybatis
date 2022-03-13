package com.jerico.springboot.mybatis.service.impl;

import com.jerico.springboot.mybatis.dao.UserDAO;
import com.jerico.springboot.mybatis.entity.UserEntity;
import com.jerico.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Override
    public int saveUser(UserEntity userEntity) {
        return userDAO.insertUser(userEntity);
    }

    @Override
    public UserEntity getUser(int id) {
        return userDAO.getUser(id);
    }
}
