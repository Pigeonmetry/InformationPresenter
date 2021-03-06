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

    //注册
    @Override
    public int saveUser(String email, String password) {
        int result = userMapper.saveUser(email,password);
        return result;
    }
    //登录
    @Override
    public User login(String email, String password) {

        return userMapper.login(email,password);
    }
    //验证邮箱
    @Override
    public User seleteEmail(String email) {
        return userMapper.seleteEmail(email);
    }

    //保存个人信息
    @Override
    public int insertUser(String username,String sex,String height ,String phone,String email, String education,String school,String address,String skills,String text,String password) {
        int count = userMapper.insertUser( username, sex, height  ,phone, email,  education, school, address, skills, text, password);
        return count;
    }

    @Override
    public User seleteAll(String email) {
        User u =userMapper.seleteAll(email);
        return u;
    }

    @Override
    public int getpassword(String email, String password) {
        return userMapper.getpassword(email, password);
    }


}
