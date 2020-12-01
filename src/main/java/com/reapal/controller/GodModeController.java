package com.reapal.controller;

import com.reapal.utils.SpringContextUtil;
import groovy.lang.GroovyClassLoader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName GodModeControllelr.java
 * @Description 在线动态执行代码(上帝模式)
 * @createTime 2020年06月19日 14:25:00
 */
@RequestMapping("/god")
@RestController
public class GodModeController {


    /**
     * https://yq.aliyun.com/articles/665345
     * @param godReq
     * @return
     * @throws Exception
     */
    @PostMapping("/run-script")
    public Object runScript(@RequestBody GodReq godReq) throws Exception {
        // 这里其实就是groovy的api动态的加载生成一个Class，然后反射生成对象，然后执行run方法，最后返回结果
        // 最精华的地方就是SpringContextUtils.autowireBean，可以实现自动注入bean，
        if(!StringUtils.isEmpty(godReq.getScript())){
            Class clazz =new GroovyClassLoader().parseClass(godReq.getScript());
            Method run = clazz.getMethod("run");
            final Object instance = clazz.newInstance();
            SpringContextUtil.autowireBean(instance);
            Object result = run.invoke(instance);
            return result;
        }else{
            return null;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GodReq{
        String script;
    }

}
