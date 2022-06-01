package com.xinke.spring.controller;

import com.google.gson.Gson;
import com.xinke.spring.mapper.UserMapper;
import com.xinke.spring.bean.User;
import com.xinke.spring.service.UserService;
import com.xinke.spring.service.imp.ImpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

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
    @RequestMapping("/api/login")
    public String login( @RequestBody User user) {
          String flag = "fail";
          User u = userService.login(user.getEmail(),user.getPassword());
        if (u != null && StringUtils.hasLength(user.getPassword())) {
            // 返回给前端的数据设置（登录成功）
            flag = "ok";
        }
        Map<String , Object> map =  new HashMap<>();
        map.put("flag",flag);
        map.put("user",user);
        Gson gson = new Gson();
        String param = gson.toJson(map);
        return param;
    }
    @RequestMapping("/api/register")
    public String register(HttpSession session,@Param("email")String email, @Param("password") String password,Model model) {

        if(StringUtils.hasLength(email)==false){
            model.addAttribute("msg", "注册失败，邮箱未填写完整");
            return "index";
        }
        if(StringUtils.hasLength(password)==false){
            model.addAttribute("msg", "注册失败，密码未填写完整");
            return "index";
        }
        User user = userService.seleteEmail(email);
        session.setAttribute("registerUser", user);
        if(user !=null){
             model.addAttribute("msg", "注册失败,邮箱已被注册");
            return "index";
        }
        else{
            userService.saveUser(email,password);
            model.addAttribute("msg", "注册成功,您可以登录了");
            return "index";
        }
    }
}
