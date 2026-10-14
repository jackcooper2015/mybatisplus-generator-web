package com.reapal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.reapal.dao.DbConfigDao;
import com.reapal.dao.TableStrategyConfigDao;
import com.reapal.dao.TemplateDao;
import com.reapal.model.*;
import com.reapal.service.CodeService;
import com.reapal.service.CodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


@Controller
@RequestMapping
public class CodeController extends BaseController{

	@Autowired
	private CodeService codeService;

    @Autowired
    private CodeGenerationService codeGenerationService;

	@Autowired
	private DbConfigDao dbConfigDao;

	@Autowired
	private Environment env;

	@Autowired
	private TemplateDao templateDao;

	@Autowired
	private TableStrategyConfigDao tableStrategyConfigDao;

	@RequestMapping("/index")
	public String init(HttpServletRequest request) throws UnknownHostException {
		if(request.getSession().getAttribute("user") == null){
			return "redirect:/login";
		}
		final String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		final HttpSession session = request.getSession();
		session.setAttribute("wsUrl","ws://"+hostAddress+":"+port+"/chat");
		return "/views/index/index";
	}

	@GetMapping("/getDbList")
	@ResponseBody
	public JSONObject getDbList(Model model){
		List<DbConfig> dbConfigList = dbConfigDao.findAll();
		return respJson(0,null,dbConfigList);
	}

	@GetMapping("/getByDbId")
	@ResponseBody
	public JSONObject getByDbId(Long id) throws IOException {
		DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
		return respJson(0, "", dbConfig);
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/database-list",method=RequestMethod.GET)
	public String databaseList(Model model,DbConfig dbConfig){
		return "/views/db/database_list";
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/edit",method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject edit(@RequestBody DbConfig dbConfig){
		dbConfigDao.save(dbConfig);
		return respJson(0, "修改成功", true);
	}

	/**
	 * 测试数据库配置
	 */
	@ResponseBody
	@RequestMapping(value = "/test",method=RequestMethod.POST)
	public JSONObject test( DbConfig dbConfig){
		String result = codeService.testConnection(dbConfig);
		if(org.springframework.util.ObjectUtils.isEmpty(result)){
			return respJson(0,"测试成功",result);
		}else{
			return respJson(1,"数据库链接失败",result);
		}
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save(@RequestBody DbConfig dbConfig){
		dbConfigDao.save(dbConfig);
		return respJson(0,"添加成功",true);
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(Model model,DbConfig dbConfig){
		dbConfigDao.deleteById(dbConfig.getId());
		return respJson(0, "删除成功", true);
	}

	@GetMapping("/to-table-list")
	public String toTableList(Model model) throws IOException {
		return "/views/db/tables_list";
	}

	@GetMapping("/table-list")
	@ResponseBody
	public JSONObject tableList(Long id) throws IOException {
		List<TableInfo> tableList = null;
		try {
			DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
			tableList = codeService.getAllTables(dbConfig);
		} catch (Exception e) {
			return respJson(500, "数据库链接失败",null);
		}
		return respJson(0, "succ", tableList);
	}

	@GetMapping("/to-column-list")
	public String toColumnList(){
		return "/views/db/column_list";
	}

	@GetMapping("/column-list")
	@ResponseBody
	public JSONObject columnList(String tableName,Long id) throws IOException {
		DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
		TableInfo tableInfo = codeService.getAllColumns(tableName,dbConfig);
		return respJson(0, "succ", tableInfo);
	}

	@GetMapping("/get-table-strategy")
	@ResponseBody
	public JSONObject getTableStrategy(String tableName,Long id) throws IOException {
		return respJson(0, "succ", tableStrategyConfigDao.findByDbIdAndTableName(id,tableName));
	}

	/**
	 * 保存配置信息
	 */
	@RequestMapping(value = "/columnsave",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save(Long id , String tableName,String comments , TableStrategyConfig tableStrategyConfig){
		String[] remarks = request.getParameterValues("remarks[]");
		DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(tableName);
		tableInfo.setComments(comments);
		List<ColumnInfo> listItem = new ArrayList<ColumnInfo>();
		for(String remark: remarks == null ? new String[0] : remarks){
			System.out.println(remark);
			String[] mark = remark.split("@");
			ColumnInfo item = new ColumnInfo();
			item.setColName(mark[0]);
			item.setColType(mark[1]);
			if(mark.length >= 3) {
				item.setComments(mark[2]);
			}
			if (mark.length >= 4) {
				item.setExtra(mark[3]);
			}
			if (mark.length >= 5) {
				item.setNullable("true".equalsIgnoreCase(mark[4]));
			}
			if (mark.length >= 6) {
				item.setDefaultValue(mark[5]);
			}
			listItem.add(item);
		}
		tableInfo.setListColumn(listItem);
//		codeService.saveComment(tableInfo,dbConfig);
        // The legacy UI sends the database ID as "id". Never use it as a strategy primary key.
        TableStrategyConfig existing = tableStrategyConfigDao.findByDbIdAndTableName(id, tableName);
        tableStrategyConfig.setId(existing == null ? null : existing.getId());
        tableStrategyConfig.setDbId(id);
        tableStrategyConfig.setTableName(tableName);
        tableStrategyConfigDao.save(tableStrategyConfig);
		return respJson(0, "保存成功", true);
	}

	@GetMapping("/batchGenerate")
	public String batchGenerate(String jsonStr,Long id,HttpServletResponse response){
		TableStrategyConfig tableStrategyConfig = JSON.parseObject(jsonStr, TableStrategyConfig.class);
		DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
		this.buildCodes(response, dbConfig, tableStrategyConfig);
		return null;
	}

	/**
	 * 生成代码
	 */
	@RequestMapping(value="/generate",method=RequestMethod.GET)
	public String generate(String tableName, Long id, HttpServletResponse response) throws IOException {
		DbConfig dbConfig = dbConfigDao.findById(id).orElseThrow();
        TableStrategyConfig tableStrategyConfig = tableStrategyConfigDao.findByDbIdAndTableName(id, tableName);
		this.buildCodes(response, dbConfig, tableStrategyConfig);
		return null;
	}

    /** Both routes use the same isolated generation and ZIP workflow. */
    private void buildCodes(HttpServletResponse response, DbConfig dbConfig, TableStrategyConfig options) {
        List<Template> templates = options != null && options.getTemplateSetId() != null
                && options.getTemplateSetId() > 0
                ? templateDao.findByTemplateSetId(options.getTemplateSetId()) : Collections.emptyList();
        try {
            byte[] zip = codeGenerationService.generateZip(dbConfig, options, templates);
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=src.zip");
            response.setContentLength(zip.length);
            response.getOutputStream().write(zip);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create or download generated code", e);
        }
    }
}
