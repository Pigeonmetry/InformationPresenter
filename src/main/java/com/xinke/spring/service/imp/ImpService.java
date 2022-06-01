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
        int result = userMapper.saveUser(email,password);
        return result;
    }

    @Override
    public User login(String email, String password) {

        return userMapper.login(email,password);
    }

    @Override
    public User seleteEmail(String email) {
        return userMapper.seleteEmail(email);
    }

    @Override
    public int insertUser(String username, String sex, int phone, int height, String skills, String education, String school, String address) {
        int count = userMapper.insertUser(username,sex,phone,height,skills,education,school,address);
        return count;
    }


}
