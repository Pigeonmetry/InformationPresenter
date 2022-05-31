package com.xinke.spring.service;

import com.xinke.spring.bean.User;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */

public interface UserService {

    /**
     * saveUser
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

}
