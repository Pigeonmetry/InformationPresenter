package com.xinke.spring.controller;

import com.google.gson.Gson;
import com.xinke.spring.bean.User;
import com.xinke.spring.config.MailHelper;
import com.xinke.spring.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadFactory;

/**
 * 完成用户功能
 * @author Administrator
 */
@Slf4j
//@CrossOrigin
@Controller
@RequestMapping("/api")
public class UserController {

    static long midTime;
    static Gson gson = new Gson();
    static User user;
    static String str;
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value = {"/login/test"})
    public String test(HttpServletRequest request, Model model){
        HttpSession  session = request.getSession();
        Object email = session.getAttribute("email");
        if(email==null){
            model.addAttribute("status","fail");
             str = gson.toJson(model);
            return str;
        }
        else{
            model.addAttribute("status","ok");
             str = gson.toJson(model);
            return str;}
    }

    /**
     * 登录功能
     * @param session
     * @param model
     * @param email
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpSession session, ModelMap model, @Param("email") String email, @Param("password") String password) {
        user = userService.login(email,password);
        if (user != null && StringUtils.hasLength(password)) {
            model.addAttribute("msg", "登录成功");
            model.addAttribute("status","ok");
            //保存用户email
             session.setAttribute("username",email);
             session.setAttribute("email",email);
             session.setAttribute("password",user.getPassword());
             user = userService.seleteAll(email);
             model.addAttribute("data",user);
             model.addAttribute("session",session);
             str =gson.toJson(model);
            System.out.println(str);
            return str;
        }
        else{
            model.addAttribute("msg", "账号或密码错误");
            model.addAttribute("status","fail");
             str =gson.toJson(model);
            System.out.println(str);
            return str;
        }
    }

    /**
     * 注册功能
     * @param session
     * @param email
     * @param password
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/signup")
    public String register(HttpSession session,@Param("email")String email, @Param("password") String password,Model model) {

        if(StringUtils.hasLength(email)==false){
            model.addAttribute("msg", "注册失败，邮箱未填写完整");
            model.addAttribute("status","fail");
             str =gson.toJson(model);
            return str;
        }
        if(StringUtils.hasLength(password)==false){
            model.addAttribute("msg", "注册失败，密码未填写完整");
            model.addAttribute("status","fail");
             str =gson.toJson(model);
            return str;
        }
        User user = userService.seleteEmail(email);
        session.setAttribute("registerUser", user);
        if(user !=null){
             model.addAttribute("msg", "注册失败,邮箱已被注册");
             model.addAttribute("status","fail");
             str =gson.toJson(model);
            return str;
        }
        else{
            userService.saveUser(email,password);
            model.addAttribute("msg", "注册成功,您可以登录了");
            model.addAttribute("status","ok");
             str =gson.toJson(model);
            return str;
        }
    }

    /**
     * 更改资料功能
     * @param session
     * @param model
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/info/update")
    public String message(HttpSession session,Model model,@RequestParam Map<Object, String> params){
        System.out.println("返回信息"+params);
        String username = params.get("username");
        String sex = params.get("sex");
        String phone = params.get("phone");
        String height = params.get("height");
        String skills = params.get("skills");
        String education = params.get("education");
        String school = params.get("school");
        String address = params.get("address");
        String text = params.get("text");
         user = userService.seleteEmail((String) session.getAttribute("email"));
        if (user == null) {
            model.addAttribute("msg", "邮箱不存在,请重新确认或选择重新注册邮箱");
            model.addAttribute("status","fail");
             str =gson.toJson(model);
            return str;
        }
        else {
            userService.insertUser((String) session.getAttribute("email"),username,sex,phone,height,skills,text,education,school,address);
            User u = userService.seleteAll((String) session.getAttribute("email"));
            model.addAttribute("msg", "成功修改");
            model.addAttribute("status","ok");
             str =gson.toJson(model);
            return str;
        }
    }
    /**
     *头像上传功能
     * MultipartFile 自动封装上传过来的文件
     * @param headerImg
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/avatar/upload",produces = MediaType.IMAGE_JPEG_VALUE)
    public String upload(Model model,HttpSession session,String username,
                         @RequestPart("Img") MultipartFile headerImg) throws IOException {
        String path ="C:\\spring\\spring\\src\\main\\resources\\static\\File\\"+session.getAttribute("email")+".jpg";
        File file = new File(path);
        log.info("上传的信息：email={}，username={}，headerImg={}，photos={}",
                 headerImg.getSize());
        if (!headerImg.isEmpty()) {
            //路径是个文件且不为空时删除文件
            if(file.isFile()&&file.exists()){
                file.delete();
            }
            //保存到文件服务器
            headerImg.transferTo(new File(path));
            model.addAttribute("msg","上传成功");
            model.addAttribute("status","ok");
        }
        else{
            model.addAttribute("msg","失败");
            model.addAttribute("status","fail");
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();

         str = gson.toJson(model);
        return str;
    }

    /**
     * 获取头像功能
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/avatar/get")
    public Object download(HttpSession session,MultipartFile headerImg) throws IOException {
        // 指定下载文件
        String path ="C:\\spring\\spring\\src\\main\\resources\\static\\File\\"+session.getAttribute("email")+".jpg";
        String path2 ="C:\\spring\\spring\\src\\main\\resources\\static\\File\\"+"null"+".jpg";
        File file = new File(path);
        File file2 = new File(path2);
        if(!file.exists()){
            InputStream fileInputStream2 = new FileInputStream(file2);
            byte[] body2 = new byte[fileInputStream2.available()];
            fileInputStream2.read(body2);
            String name2 = file.getName();
            String downLoadFileName2 = new String(name2.getBytes("utf-8"), "ISO-8859-1");
            HttpHeaders httpHeaders2 = new HttpHeaders();
            httpHeaders2.add("Content-Disposition", "attachment;fileName=" + downLoadFileName2);
            fileInputStream2.close();
            HttpStatus status2 = HttpStatus.OK;
            return new ResponseEntity<byte[]>(body2, httpHeaders2, status2);
        }
        else {
            // 获取文件的输入流
            InputStream fileInputStream = new FileInputStream(file);
            // 创建字符数组，并设置数组的大小为预估的字节数
            byte[] body = new byte[fileInputStream.available()];
            // 将输入流存储在缓存数组中
            fileInputStream.read(body);
            // 获取下载显示的文件名，并解决中文乱码
            String name = file.getName();
            String downLoadFileName = new String(name.getBytes("utf-8"), "ISO-8859-1");
            // 设置Http响应头信息，并且通知浏览器以附件的形式进行下载
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attachment;fileName=" + downLoadFileName);
            // 设置Http响应状态信息
            HttpStatus status = HttpStatus.OK;
            fileInputStream.close();
            return new ResponseEntity<byte[]>(body, httpHeaders, status);
        }
    }
    //创建线程池,用来完成验证码分线程发送，防止拥堵
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder();

    /**
     *发送修改密码的验证码
     * @param email
     * @param model
     * @param session
     * @return
     * @throws GeneralSecurityException
     * @throws MessagingException
     */
    @ResponseBody
    @RequestMapping("/code")
    public String code(@Param("email")String email, Model model, HttpSession session) throws GeneralSecurityException, MessagingException {

        String code = String.format("%06d",(int)(Math.random()*1000000));
        session.setAttribute("code",code);
        MailHelper hlpers = new MailHelper("smtp.qq.com","865212021@qq.com","kwssmmasfjasbaii");
        new Thread(new Runnable(){
            @Override
            @SneakyThrows
            public void run(){
        hlpers.sendMail(email,"验证码查收",code);
            }
        }).start();
        if(userService.seleteEmail(email)==null){
            model.addAttribute("msg","请确认，邮箱没注册");
        }
        else{
            session.setAttribute("email",email);
        }
//        while (midTime > 0) {
//            midTime--;
//            long ss = midTime % 60;
//            if(midTime == 0) {
//                model.addAttribute("msg", "请重新发送验证码");
//            }
//
//        }
        model.addAttribute("msg","验证码已发送,请查收");
        model.addAttribute("status","ok");

         str = gson.toJson(model);
        return str;
    }

    /**
     * 找回密码
     * @param model
     * @param session
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/retrieve")
    public String getpassword(Model model,HttpSession session,@RequestParam Map<Object, String> params){
        String password = params.get("password");
        String email = params.get("email");
        String otp  = params.get("otp");
        String code = String.valueOf(session.getAttribute("code"));
        System.out.println("验证码"+code);
        System.out.println(otp);
        if(email == null){
            model.addAttribute("msg","不能为空,请重新填写邮箱");
            model.addAttribute("status","fail");
        }if(password == null){
            model.addAttribute("msg","不能为空,请重新填写密码");
            model.addAttribute("status","fail");
        }if(otp == null|| code.equals(otp) == false){
            model.addAttribute("msg","验证码错误,请重新填写验证码");
            model.addAttribute("status","fail");
        }else {
            userService.getpassword(email,password);
            model.addAttribute("msg","修改成功");
            model.addAttribute("status","ok");
        }
        str = gson.toJson(model);
        System.out.println(str);
        return str;
    }

    /**
     * 修改密码
     * @param model
     * @param session
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/modify")
    public String changepassword( Model model, HttpSession session, @Param("password") String password){

       str =  (String)session.getAttribute("email");
        System.out.println(str);
        int  i = userService.getpassword(str,password);
        if(i==0){
            model.addAttribute("msg","账号异常,修改失败");
            model.addAttribute("status","fail");
        }
        else {
            model.addAttribute("msg", "修改成功");
            model.addAttribute("status", "ok");
            }
         str = gson.toJson(model);
        return str;
    }

}
