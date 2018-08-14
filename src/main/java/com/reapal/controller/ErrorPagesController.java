package com.reapal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jack-cooper on 2017/1/22.
 */
@Controller
public class ErrorPagesController {
    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "/views/404";
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        return "/views/403";
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError() {
        return "/views/500";
    }
}
