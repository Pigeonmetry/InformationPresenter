package com.xinke.spring.controller;

import com.xinke.spring.mapper.UserMapper;
import com.xinke.spring.bean.User;
import com.xinke.spring.service.UserService;
import com.xinke.spring.service.imp.ImpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public String login(String email, String password) {
        User user = userService.login(email,password);
        if(user == null){
            return "登录失败";
        }
        return "登录成功";
    }


    @RequestMapping("/register")
    public String register(@Param("email")String email, @Param("password") String password) {
        int result = userService.saveUser(email,password);
        if(result == 0){
            return "fail";
        }
        return "success";
    }
}
