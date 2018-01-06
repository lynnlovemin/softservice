package com.lynn.user.service;

import com.lynn.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected UserMapper userMapper;
}
