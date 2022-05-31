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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/","/index"})
    public String indexpage(){

        return "index";
    }

    @PostMapping("/tologin")
    public String login(Model model,HttpServletRequest request, HttpSession session, @RequestParam Map<String, String> parameter) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userService.login(email,password);
        if (user != null && StringUtils.hasLength(user.getPassword())) {
            // 把当前用户信息存到session里，用于后续操作
            session.setAttribute("loginUser", user);
            // 返回给前端的数据设置（登录成功）
            parameter.put("message", "登录成功");
            parameter.put("status", "ok");
            return "redirect:success";
        }
        else{
        // 返回给前端的数据设置(登录失败)
        model.addAttribute("message", "登录失败，账号密码错误");
        return "index";
    }
    }
    @GetMapping("/success")
    public String successpage(){
        return "success";
    }


    @GetMapping("/toregister")
    public String register(@Param("email")String email, @Param("password") String password) {
        int result = userService.saveUser(email,password);
        if(result == 0){
            return "fail";
        }
        return "success";
    }
}
