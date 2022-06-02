package com.xinke.spring.controller;

import com.google.gson.Gson;
import com.xinke.spring.bean.User;
import com.xinke.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
//@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping(value = {"/","api/index"})
//    public String indexpage(){
//
//        return "index";
//    }
    @RequestMapping("/login")
    public String login(Model model,@Param("email") String email,@Param("password") String password) {
        Gson gson = new Gson();
        User u = userService.login(email,password);
        if (u != null && StringUtils.hasLength(password)) {
            // 返回给前端的数据设置（登录成功）
            model.addAttribute("msg", "登录成功");
            model.addAttribute("status","ok");
            String str =gson.toJson(model);
        return str;
        }
        else{
            model.addAttribute("msg", "登录失败，请检查账号密码");
            model.addAttribute("status","fail");
            String str =gson.toJson(model);
            return str;
        }
    }
    @RequestMapping("/register")
    public String register(HttpSession session,@Param("email")String email, @Param("password") String password,Model model) {
        Gson gson = new Gson();
        if(StringUtils.hasLength(email)==false){
            model.addAttribute("msg", "注册失败，邮箱未填写完整");
            model.addAttribute("status","fail");
            String str =gson.toJson(model);
            return str;
        }
        if(StringUtils.hasLength(password)==false){
            model.addAttribute("msg", "注册失败，密码未填写完整");
            model.addAttribute("status","fail");
            String str =gson.toJson(model);
            return str;
        }
        User user = userService.seleteEmail(email);
        session.setAttribute("registerUser", user);
        if(user !=null){
             model.addAttribute("msg", "注册失败,邮箱已被注册");
             model.addAttribute("status","fail");
            String str =gson.toJson(model);
            return str;
        }
        else{
            userService.saveUser(email,password);
            model.addAttribute("msg", "注册成功,您可以登录了");
            model.addAttribute("status","ok");
            String str =gson.toJson(model);
            return str;
        }
    }

    @RequestMapping("/message")
    public String message(Model model,@RequestParam Map<Object, String> params){
        String email = params.get("email");
        String username = params.get("username");
        String sex = params.get("sex");
        String phone = params.get("phone");
        String height = params.get("height");
        String skills = params.get("skills");
        String education = params.get("education");
        String school = params.get("school");
        String address = params.get("address");
        String text = params.get("text");
        User user = userService.seleteEmail(email);
        if (user == null) {
            model.addAttribute("msg", "邮箱不存在,请重新确认或选择重新注册邮箱");
            model.addAttribute("status","fail");
            Gson gson = new Gson();
            String str =gson.toJson(model);
            return str;
        }
        else {
            userService.insertUser(email,username,sex,phone,height,skills,education,school,address,text);
            User u = userService.seleteAll(email);
            Gson gson = new Gson();
            String str =gson.toJson(u);
            return str;
        }

    }

}
