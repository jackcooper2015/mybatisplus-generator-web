package com.reapal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName EditorController.java
 * @Description 在线编辑器
 * @createTime 2018年12月27日 10:23:00
 */
@Controller
@RequestMapping("/editor")
public class EditorController extends BaseController {

    @GetMapping(value = "/to-editor")
    public String toEditor(){
        return "/views/editor/editor";
    }

}
