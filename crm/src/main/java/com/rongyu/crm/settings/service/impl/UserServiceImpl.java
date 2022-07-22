package com.rongyu.crm.settings.service.impl;

import com.rongyu.crm.settings.domain.User;
import com.rongyu.crm.settings.mapper.UserMapper;
import com.rongyu.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndLoginPwd(Map<String, Object> map) {
        User user = userMapper.selectUserByLoginActAndLoginPwd(map);
        return user;
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }
}
