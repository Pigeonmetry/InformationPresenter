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
    int insertUser(String email,String username,String sex ,String phone,
                   String height,String skills,String text,
                   String education,String school,String address);
    /**
     * 查询所有信息
     * @param email
     * @return user
     */
    User seleteAll(@Param("email") String email);
}
