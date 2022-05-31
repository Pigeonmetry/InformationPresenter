package com.xinke.spring.service.imp;

import com.xinke.spring.bean.User;
import com.xinke.spring.mapper.UserMapper;

import com.xinke.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class ImpService implements UserService {

    @Autowired(required = false)
     UserMapper userMapper;


    @Override
    public int saveUser(String email, String password) {
        int user = userMapper.saveUser(email,password);
        return user;
    }

    @Override
    public User login(String email, String password) {

        return userMapper.login(email,password);
    }


}
