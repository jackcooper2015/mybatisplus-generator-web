package com.reapal.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by jack-cooper on 2017/1/14.
 */
@Controller
public class LoginController extends BaseController{

    //加密过程在这里体现
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login() {
        // 1、收集参数、验证参数
        // 2、绑定参数到命令对象
        // 3、将命令对象传入业务对象进行业务处理
        // 4、选择下一个页面
        ModelAndView mv = new ModelAndView();
        // 添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "welcome login repal.com");
        // 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("views/login");
        return mv;
    }



}
