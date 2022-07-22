package com.rongyu.crm.settings.service;

import com.rongyu.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByLoginActAndLoginPwd(Map<String,Object> map);
    List<User> queryAllUsers();
}
