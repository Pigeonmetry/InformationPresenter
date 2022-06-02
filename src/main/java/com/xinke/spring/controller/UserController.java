package com.xinke.spring.controller;

import com.google.gson.Gson;
import com.xinke.spring.bean.User;
import com.xinke.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 */
@Slf4j
//@CrossOrigin
@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping(value = {"/","api/index"})
//    public String indexpage(){
//
//        return "index";
//    }
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpSession Session,Model model,@Param("email") String email,@Param("password") String password) {
        Gson gson = new Gson();
        User u = userService.login(email,password);
        if (u != null && StringUtils.hasLength(password)) {
            // 返回给前端的数据设置（登录成功）
            model.addAttribute("msg", "登录成功");
            model.addAttribute("status","ok");
            u = userService.seleteAll(email);
            String str =gson.toJson(u);
        return str;
        }
        else{
            model.addAttribute("msg", "登录失败，请检查账号密码");
            model.addAttribute("status","fail");
            String str =gson.toJson(model);
            return str;
        }
    }
    @ResponseBody
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
    @ResponseBody
    @RequestMapping(value="/info/update,")
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
    /**
     * MultipartFile 自动封装上传过来的文件
     *
     * @param username
     * @param username
     * @param headerImg
     * @return
     */
    @RequestMapping(value="/upload",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] upload(Model model,
                         @RequestParam("username") String username,
                         @RequestPart("Img") MultipartFile headerImg) throws IOException {
        String path ="C:\\spring\\spring\\src\\main\\resources\\static\\File\\"+username+".jpg";
        File file = new File(path);
        log.info("上传的信息：email={}，username={}，headerImg={}，photos={}",
                username, headerImg.getSize());
        if (!headerImg.isEmpty()) {
            String originalFilename = headerImg.getOriginalFilename();
            //路径是个文件且不为空时删除文件
            if(file.isFile()&&file.exists()){
                file.delete();
            }
            //保存到文件服务器
            headerImg.transferTo(new File(path));
            model.addAttribute("msg","上传成功");
        }
        else{
            model.addAttribute("msg","失败");
            model.addAttribute("status","fail");
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }



}
