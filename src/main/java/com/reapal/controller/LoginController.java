package com.reapal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by jack-cooper on 2017/1/14.
 */
@Controller
public class LoginController extends BaseController{
    private final String adminUsername;
    private final String adminPassword;

    public LoginController(@Value("${generator.admin.username:admin}") String adminUsername,
                           @Value("${generator.admin.password}") String adminPassword) {
        if (adminUsername.isBlank() || adminPassword.isBlank()) {
            throw new IllegalArgumentException("GENERATOR_ADMIN_USERNAME and GENERATOR_ADMIN_PASSWORD must not be blank");
        }
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }


    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping(value = "/login")
    public ModelAndView toLogin() {
        // 1、收集参数、验证参数
        // 2、绑定参数到命令对象
        // 3、将命令对象传入业务对象进行业务处理
        // 4、选择下一个页面
        ModelAndView mv = new ModelAndView();
        // 添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "welcome login repal.com");
        // 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("/views/index/login");
        return mv;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public boolean login(String username,String password){
        if(adminUsername.equals(username) && password != null && MessageDigest.isEqual(
                adminPassword.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8))){
            request.getSession();
            request.changeSessionId();
            request.getSession().setAttribute("user",username);
            return true;
        }
        return false;
    }

    /**
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        request.getSession().invalidate();
        return "redirect:/login";
    }



}
