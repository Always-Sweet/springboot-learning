package com.slm.hikari.service;

import com.slm.hikari.entity.User;
import com.slm.hikari.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> query() {
        return userMapper.query();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long create(User user) {
        userMapper.add(user);
        return user.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void modify(User user) {
        userMapper.update(user);
    }

}