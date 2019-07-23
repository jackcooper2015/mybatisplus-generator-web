package com.reapal.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jack-cooper on 2017/1/14.
 */
public abstract class BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;


    @Autowired
    protected HttpSession session;

    /**
     * 不能自动注入，下文使用ModelAttribute注入
     */
    private static final ThreadLocal<HttpServletResponse> responseContainer = new ThreadLocal<HttpServletResponse>();


    @ModelAttribute
    private void initResponse(HttpServletResponse response){
        this.responseContainer.set(response);
    }

    /**
     * 获取当前线程的response对象
     * @return
     */
    protected final HttpServletResponse getResponse(){
        return responseContainer.get();
    }


    /**
     * 封装响应结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject respJson(int code,String msg,Object data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if(data != null){
            jsonObject.put("data",data);
        }
        return jsonObject;
    }


}
