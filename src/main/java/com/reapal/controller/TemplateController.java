package com.reapal.controller;

import com.alibaba.fastjson.JSONObject;
import com.reapal.dao.TemplateDao;
import com.reapal.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Example;

/**
 * @author jackcooper
 */
@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {

    @Autowired
    private TemplateDao templateDao;

    /**
     * 通过id查询
     */
    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    public JSONObject getById(@PathVariable(value = "id") Long id){
        return respJson(0,"",templateDao.getOne(id));
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ResponseBody
    public JSONObject save(@RequestBody Template template){
        templateDao.save(template);
        return respJson(0, "", template);
    }

    /**
     * 通过id删除
     */
    @ResponseBody
    @DeleteMapping("/delete-by-id/{id}")
    public JSONObject delete(@PathVariable(value = "id") String ids){
        String[] idsStrs = ids.split(",");
        for (String id:idsStrs){
            templateDao.delete(Long.parseLong(id));
        }
        return respJson(0, "",null);
    }

    /**
     * 修改
     */
    @ResponseBody
    @PutMapping("/update")
    public JSONObject updateById(@RequestBody Template template){
        templateDao.save(template);
        return respJson(0, "保存成功", null);
    }


    /**
     * 查询列表
     */
    @ResponseBody
    @PostMapping("/list")
    public JSONObject list(@RequestBody Template template){
        Example<Template> e = Example.of(template);
        return respJson(0, "", templateDao.findAll(e));
    }

}
