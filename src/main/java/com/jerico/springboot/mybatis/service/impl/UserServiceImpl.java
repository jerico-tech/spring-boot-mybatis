package com.jerico.springboot.mybatis.service.impl;

import com.jerico.springboot.mybatis.dao.UserDAO;
import com.jerico.springboot.mybatis.entity.UserEntity;
import com.jerico.springboot.mybatis.service.UserService;
import com.jerico.springboot.mybatis.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserEntity getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

    @Override
    public List<UserEntity> listUsers() {
        return userDAO.listUsers();
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        userDAO.updateUser(userEntity);
        return userEntity;
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List<UserEntity> batchSaveUser(List<UserEntity> userEntityList) {
        userDAO.batchInsertUser(userEntityList);
        return userEntityList;
    }

    @Override
    public List<UserEntity> batchUpdateUser(List<UserEntity> userEntityList) {
        userDAO.batchUpdateUser(userEntityList);
        return userEntityList;
    }

    @Override
    public void batchRemoveUser(List<Integer> userEntityList) {
        userDAO.batchDeleteUser(userEntityList);
    }

}
