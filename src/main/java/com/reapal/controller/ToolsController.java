package com.reapal.controller;

import com.alibaba.fastjson.JSONObject;
import com.reapal.utils.ToStringUtils;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.text.ParseException;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName EditorController.java
 * @Description 在线编辑器
 * @createTime 2018年12月27日 10:23:00
 */
@Controller
@RequestMapping("/tools")
public class ToolsController extends BaseController {

    @GetMapping(value = "/to-markdown")
    public String toMarkDownEditor(){
        return "/views/tools/markdown";
    }


    @GetMapping(value = "/to-editor")
    public String toEditor(){
        return "/views/tools/editor";
    }


    @GetMapping(value = "/to-flowchart")
    public String toFlowChart(){
        return "/views/tools/flow_chart";
    }

    @GetMapping(value = "/to-statechart")
    public String toStateChart(){
        return "/views/tools/state_chart";
    }

    @GetMapping(value = "/to-onlinechat")
    public String toOnlineChart(){
        return "/views/tools/online_chat";
    }

    @GetMapping(value = "/to-form-making")
    public String toFormMaking(){
        return "/views/tools/form_making";
    }


    @GetMapping(value = "/to-big-screen")
    public String toBigScreen(){
        return "/views/tools/big_screen";
    }

    @GetMapping(value = "/to-vchart")
    public String toViser(){
        return "/views/tools/vchart_demo";
    }

    @GetMapping(value = "/to-code-diff")
    public String toCodeDiff(){
        return "/views/tools/code-diff";
    }


    @GetMapping(value = "/to-music")
    public String toMusic(){
        return "/views/tools/mp3_player";
    }


    @GetMapping(value = "/to-jsonsfmt")
    public String toJsonFormat(){
        return "/views/tools/jsonformat";
    }

    @PostMapping("/javastr2json")
    @ResponseBody
    public JSONObject java2Json(@RequestBody Commonmodel commonmodel) throws ParseException {
        return respJson(0, "", ToStringUtils.toJSONString(commonmodel.getData()));
    }

    @Data
    public static class Commonmodel implements Serializable {
        private String data;
    }


}
