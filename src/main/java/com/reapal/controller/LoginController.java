package com.reapal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by jack-cooper on 2017/1/14.
 */
@Controller
public class LoginController extends BaseController{

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
        if("admin".equals(username) && "admin".equals(password)){
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
