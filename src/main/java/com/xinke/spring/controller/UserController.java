package com.xinke.spring.controller;

import com.google.gson.Gson;
import com.xinke.spring.bean.User;
import com.xinke.spring.config.MailHelper;
import com.xinke.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 完成用户功能
 *
 * @author Administrator
 */
@Slf4j
//@CrossOrigin
@Controller
@RequestMapping("/api")
public class UserController {

    static File avatarDir = new File("avatar");
    static MailHelper helper;

    static {
        try {
            helper = new MailHelper("smtp.qq.com", "865212021@qq.com", "kwssmmasfjasbaii");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        if (!avatarDir.exists()) {avatarDir.mkdirs();}
    }

    static long midTime;
    static Gson gson = new Gson();
    static User user;
    static String str;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = {"/login/test"})
    public String test(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object email = session.getAttribute("email");
        model.addAttribute("status", email != null ? "ok" : "fail");
        str = gson.toJson(model);
        return str;
    }

    /**
     * 登录功能
     *
     * @param session
     * @param model
     * @param email
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpSession session, ModelMap model, @Param("email") String email, @Param("password") String password) {
        user = userService.login(email, password);
        if (user != null && StringUtils.hasLength(password)) {
            //保存用户email
            session.setAttribute("email", email);
            user = userService.seleteAll(email);
            model.addAttribute("msg", "登录成功");
            model.addAttribute("status", "ok");
            model.addAttribute("data", user);
        } else {
            model.addAttribute("msg", "账号或密码错误");
            model.addAttribute("status", "fail");
        }
        str = gson.toJson(model);
        System.out.println(str);
        return str;
    }

    /**
     * 注册功能
     *
     * @param session
     * @param email
     * @param password
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/signup")
    public String register(HttpSession session, @Param("email") String email, @Param("password") String password, Model model) {

        if (!StringUtils.hasLength(email) || !StringUtils.hasLength(password)) {
            model.addAttribute("msg", "注册失败，邮箱或密码未填写完整");
            model.addAttribute("status", "fail");
        }
        if(password.length()<6||password.length()>16){
            model.addAttribute("msg", "注册失败,密码长度应介于6到16之前");
            model.addAttribute("status", "fail");
        }
        User user = userService.seleteEmail(email);
        if (user != null) {
            model.addAttribute("msg", "注册失败,邮箱已被注册");
            model.addAttribute("status", "fail");
        } else {
            userService.saveUser(email, password);
            model.addAttribute("msg", "注册成功,您可以登录了");
            model.addAttribute("status", "ok");
        }
        str = gson.toJson(model);
        return str;
    }

    /**
     * 更改资料功能
     *
     * @param session
     * @param model
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info/update")
    public String message(HttpSession session, Model model, @RequestParam Map<Object, String> params) {
        System.out.println("返回信息" + params);
        String username = params.get("username");
        String sex = params.get("sex");
        String password = params.get("password");
        String height = params.get("height");
        String phone = params.get("phone");
        String email = params.get("email");
        String skills = params.get("skills");
        String education = params.get("education");
        String school = params.get("school");
        String address = params.get("address");
        String text = params.get("text");
        user = userService.seleteEmail(email);
        if (user == null) {
            model.addAttribute("msg", "邮箱不存在,请重新确认或选择重新注册邮箱");
            model.addAttribute("status", "fail");
        } else {
            int rs = userService.insertUser(username,sex,  height, phone, email, education,school , address,skills,text,password);
            model.addAttribute("msg", rs > 0 ? "修改成功" : "修改失败");
            model.addAttribute("status", "ok");
        }
        str = gson.toJson(model);
        return str;
    }

    /**
     * 头像上传功能
     * MultipartFile 自动封装上传过来的文件
     *
     * @param headerImg
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/avatar/upload", produces = MediaType.IMAGE_JPEG_VALUE)
    public String upload(Model model, HttpSession session, String username,
                         @RequestPart("Img") MultipartFile headerImg) throws IOException {
        String filename = session.getAttribute("email") + ".jpg";
        File file = new File(avatarDir, filename);
        log.info("上传的信息：email={}，username={}，headerImg={}，photos={}",
                headerImg.getSize());
        if (!headerImg.isEmpty()) {
            //保存到文件服务器
            headerImg.transferTo(file.getAbsoluteFile());
            model.addAttribute("msg", "上传成功");
            model.addAttribute("status", "ok");
        } else {
            model.addAttribute("msg", "失败");
            model.addAttribute("status", "fail");
        }
        str = gson.toJson(model);
        return str;
    }

    /**
     * 获取头像功能
     *
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/avatar/get")
    public Object download(HttpSession session) throws IOException {
        // 指定下载文件
        String filename = session.getAttribute("email") + ".jpg";
        File file = new File(avatarDir, filename);
        if (!file.exists()) {
            return new ResponseEntity<>(new byte[0], HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
        }
    }

    //创建线程池,用来完成验证码分线程发送，防止拥堵
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(99)
    );

    /**
     * 发送修改密码的验证码
     *
     * @param email
     * @param model
     * @param session
     * @return
     * @throws GeneralSecurityException
     * @throws MessagingException
     */
    @ResponseBody
    @RequestMapping("/code")
    public String code(@Param("email") String email, Model model, HttpSession session) throws GeneralSecurityException, MessagingException {
        if (userService.seleteEmail(email) == null) {
            model.addAttribute("msg", "该邮箱未注册");
            model.addAttribute("status", "fail");
        }else {
            String code = String.format("%06d", (int) (Math.random() * 1000000));
            session.setAttribute("code", code);
            session.setAttribute("otp_email",email);
            executor.execute(() -> {
                try {
                    helper.sendMail(email, "[个人信息展示系统]找回密码", "您的验证码为："+ code);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
            model.addAttribute("msg", "验证码已发送,请查收");
            model.addAttribute("status", "ok");
        }
        str = gson.toJson(model);
        return str;
    }

    /**
     * 找回密码
     *
     * @param model
     * @param session
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/retrieve")
    public String getpassword(Model model, HttpSession session, @RequestParam Map<Object, String> params) {
        String password = params.get("password");
        String email = params.get("email");
        String otp = params.get("otp");
        String code = session.getAttribute("code").toString();
        System.out.println("验证码" + code);
        System.out.println(otp);
        if (!StringUtils.hasLength(email)) {
            model.addAttribute("msg", "邮箱不能为空,请重新填写邮箱");
            model.addAttribute("status", "fail");
        }
        else if (!StringUtils.hasLength(password)) {
            model.addAttribute("msg", "密码不能为空,请重新填写密码");
            model.addAttribute("status", "fail");
        }
        else if (!code.equals(otp)||!email.equals(session.getAttribute("otp_email").toString())) {
            model.addAttribute("msg", "验证码错误,请重新填写验证码");
            model.addAttribute("status", "fail");
        } else {
            userService.getpassword(email, password);
            model.addAttribute("msg", "修改成功");
            model.addAttribute("status", "ok");
        }
        str = gson.toJson(model);
        System.out.println(str);
        return str;
    }

    /**
     * 修改密码
     *
     * @param model
     * @param session
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/modify")
    public String changepassword(Model model, HttpSession session, @Param("password") String password) {

        str = (String) session.getAttribute("email");
        System.out.println(str);
        int i = userService.getpassword(str, password);
        if (i == 0) {
            model.addAttribute("msg", "账号异常,修改失败");
            model.addAttribute("status", "fail");
        } else {
            model.addAttribute("msg", "修改成功");
            model.addAttribute("status", "ok");
        }
        str = gson.toJson(model);
        return str;
    }

}
