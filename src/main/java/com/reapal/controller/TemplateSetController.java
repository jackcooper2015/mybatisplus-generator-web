package com.reapal.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.reapal.dao.TemplateDao;
import com.reapal.dao.TemplateSetDao;
import com.reapal.model.Template;
import com.reapal.model.TemplateSet;
import com.reapal.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jackcooper
 */
@Controller
@RequestMapping("/template-set")
public class TemplateSetController extends BaseController {

    @Autowired
    private TemplateSetDao templateSetDao;
    @Autowired
    private TemplateDao templateDao;


    @GetMapping("/to-page")
    public String toPage(){
        return "/views/db/template-set";
    }


    /**
     * 通过id查询
     */
    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    public JSONObject getById(@PathVariable(value = "id") Long id){
        return respJson(0,"",templateSetDao.getOne(id));
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public JSONObject save(@RequestBody TemplateSet templateSet) throws IOException {
        templateSetDao.save(templateSet);

        ClassPathResource pathResource = new ClassPathResource("templates"+File.separator+"gen-template");
        String path = pathResource.getFile().getAbsolutePath();
        File file = new File(path);
        File[] files = file.listFiles();
        assert files != null;
        List<Template> templates = Arrays.stream(files).map(t -> Template.builder().templateSetId(templateSet.getId()).templateName(t.getName()).content(FileUtil.readString(t, StandardCharsets.UTF_8)).build()).collect(Collectors.toList());
        templateDao.save(templates);
        return respJson(0, "", templateSet);
    }

    /**
     * 通过id删除
     */
    @ResponseBody
    @DeleteMapping("/delete-by-id/{id}")
    public JSONObject delete(@PathVariable(value = "id") String ids){
        String[] idsStrs = ids.split(",");
        for (String id:idsStrs){
            templateSetDao.delete(Long.parseLong(id));
        }
        return respJson(0, "",null);
    }

    /**
     * 修改
     */
    @ResponseBody
    @PutMapping("/update")
    public JSONObject updateById(@RequestBody TemplateSet templateSet){
        templateSetDao.save(templateSet);
        return respJson(0, "", null);
    }


    /**
     * 查询列表
     */
    @ResponseBody
    @PostMapping("/list")
    public JSONObject list(){
        return respJson(0, "", templateSetDao.findAll());
    }

}
