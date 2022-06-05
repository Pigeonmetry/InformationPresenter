package com.xinke.spring.service;

import com.xinke.spring.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */

public interface UserService {

    /**
     * register
     * @param email
     * @param password
     * @return
     */
    int saveUser( String email, String password);

    /**
     * login
     * @param email
     * @param password
     * @return
     */
    User login(String email, String password);

    /**
     * Validation email
     * @param email
     * @return int
     */
    User seleteEmail(@Param("email") String email);

    /**
     * 保存个人信息
     * @param username,sex,phone,height,skills,education,school,address
     * @return int
     */
    int insertUser(String username,String sex,String height ,String phone,String email, String education,String school,String address,String skills,String text,String password);
    /**
     * 查询所有信息
     * @param email
     * @return user
     */
    User seleteAll(@Param("email") String email);

    /**
     * 找回密码
     * @param email
     * @param password
     * @return
     */
    int getpassword(String email,String password);
}
