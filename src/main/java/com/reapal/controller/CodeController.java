package com.reapal.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.reapal.model.CodeConfig;
import com.reapal.model.ColumnInfo;
import com.reapal.model.DbConfig;
import com.reapal.model.TableInfo;
import com.reapal.service.CodeService;
import com.reapal.utils.DbConfigUtils;
import com.reapal.utils.FileUtils;
import com.reapal.utils.ZipFileUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping
public class CodeController extends BaseController{

	@Autowired
	private CodeService codeService;

	private static DbConfigUtils dbConfigUtils = new DbConfigUtils();

	@RequestMapping("/index")
	public String init(){
		if(request.getSession().getAttribute("user") == null){
			return "redirect:/login";
		}
		return "/views/index/index";
	}

	@GetMapping("/getDbList")
	@ResponseBody
	public JSONObject getDbList(Model model){
		List<DbConfig> dbConfigList = dbConfigUtils.getAllDbconfig();
		return respJson(0,null,dbConfigList);
	}

	@GetMapping("/getByDbName")
	@ResponseBody
	public JSONObject getByDbName(DbConfig dbConfig) throws IOException {
		dbConfig = dbConfigUtils.getDbconfigByKey(dbConfig.getDbName());
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
		dbConfigUtils.addDbconfig(dbConfig);
		return respJson(0, "修改成功", true);
	}

	/**
	 * 测试数据库配置
	 */
	@ResponseBody
	@RequestMapping(value = "/test",method=RequestMethod.POST)
	public JSONObject test( DbConfig dbConfig){
		String result = codeService.testConnection(dbConfig);
		if(StringUtils.isEmpty(result)){
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
		dbConfigUtils.addDbconfig(dbConfig);
		return respJson(0,"添加成功",true);
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(Model model,DbConfig dbConfig){
		dbConfigUtils.deleteDbconfig(dbConfig.getDbName());
		return respJson(0, "删除成功", true);
	}

	@GetMapping("/to-table-list")
	public String toTableList(Model model) throws IOException {
		return "/views/db/tables_list";
	}

	@GetMapping("/table-list")
	@ResponseBody
	public JSONObject tableList(String dbName) throws IOException {
		DbConfig dbConfig = dbConfigUtils.getDbconfigByKey(dbName);
		List<TableInfo> tableList = codeService.getAllTables(dbConfig);
		return respJson(0, "succ", tableList);
	}

	@GetMapping("/to-column-list")
	public String toColumnList(){
		return "/views/db/column_list";
	}

	@GetMapping("/column-list")
	@ResponseBody
	public JSONObject columnList(String tableName,String dbName) throws IOException {
		DbConfig dbConfig = dbConfigUtils.getDbconfigByKey(dbName);
		TableInfo tableInfo = codeService.getAllColumns(tableName,dbConfig);
		return respJson(0, "succ", tableInfo);
	}


	/**
	 * 保存配置信息
	 */
	@RequestMapping(value = "/columnsave",method=RequestMethod.POST)
	public String save(Model model,DbConfig dbConfig,TableInfo tableInfo){
		String[] arrRemark = request.getParameterValues("remarks");
		List<ColumnInfo> listItem = new ArrayList<ColumnInfo>();
		for(String remark:arrRemark){
			System.out.println(remark);
			String[] mark = remark.split("@");
			ColumnInfo item = new ColumnInfo();
			item.setColName(mark[0]);
			item.setColType(mark[1]);
			item.setComments(mark[2]);
			if (mark.length ==4) {
				item.setExtra(mark[3]);
			}
			listItem.add(item);
		}
		tableInfo.setListColumn(listItem);
		codeService.saveComment(tableInfo,dbConfig);

		return "redirect:/column/"+tableInfo.getTableName()+"?url="+dbConfig.getUrl()+"&driver="+dbConfig.getDriver()+"&username="+dbConfig.getUsername()+"&password="+dbConfig.getPassword()+"&schema="+dbConfig.getSchema();
	}

	/**
	 * 生成代码
	 */
	@RequestMapping(value="/generate",method=RequestMethod.GET)
	public String generate(String tableName,String dbName, boolean flag, HttpServletResponse response) throws IOException {
		DbConfig dbConfig = dbConfigUtils.getDbconfigByKey(dbName);
		TableInfo tableInfo = codeService.getAllColumns(tableName,dbConfig);
		String model = tableInfo.getComments().substring(tableInfo.getComments().indexOf("#")+1);
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/upload/"+request.getSession().getId());
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("Auto-generator");
		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		// gc.setServiceName("MP%sService");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(dbConfig.getDriver().indexOf("mysql")>-1?DbType.MYSQL:DbType.ORACLE);
		dsc.setDriverName(dbConfig.getDriver());
		dsc.setUsername(dbConfig.getUsername());
		dsc.setPassword(dbConfig.getPassword());
		dsc.setUrl(dbConfig.getUrl());
		mpg.setDataSource(dsc);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		if(flag && tableInfo.getTableName().indexOf("_")>0 && tableInfo.getTableName().lastIndexOf("_")!=tableInfo.getTableName().length()-1) {
			String prefix = tableInfo.getTableName().substring(0,tableInfo.getTableName().indexOf("_")+1);
			strategy.setTablePrefix(prefix);// 此处可以修改为您的表前缀
			NamingStrategy.removePrefix(tableInfo.getTableName(),prefix);// 表名生成策略
		}else{
			strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		}
		strategy.setInclude(new String[] { tableInfo.getTableName() }); // 需要生成的表
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 字段名生成策略
		strategy.setNaming(NamingStrategy.underline_to_camel);
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuliderModel(true);
		mpg.setStrategy(strategy);
		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com");
		pc.setEntity("domain");
		pc.setModuleName(model);
		mpg.setPackageInfo(pc);
		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				map.put("entityLombokModel",true);
				map.put("restControllerStyle",true);
				this.setMap(map);
			}
		};
		mpg.setCfg(cfg);
		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		 TemplateConfig tc = new TemplateConfig();
		 tc.setController("/templates/gen-template/controller.java.vm");
		 tc.setEntity("/templates/gen-template/entity.java.vm");
		 tc.setMapper("/templates/gen-template/mapper.java.vm");
		 tc.setXml("/templates/gen-template/mapper.xml.vm");
		 tc.setService("/templates/gen-template/service.java.vm");
		 tc.setServiceImpl("/templates/gen-template/serviceImpl.java.vm");
		 mpg.setTemplate(tc);
		// 执行生成
		mpg.execute();
		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));

		//打包下载
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition","attachment; filename=src.zip");
		try {
			ZipFileUtils zip = new ZipFileUtils();
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			String fileName = request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/upload/"+request.getSession().getId();
			File ff = new File(fileName);
			if(!ff.exists()){
				ff.mkdirs();
			}
			zip.zip(ff,zos,"");

			zos.flush();
			zos.close();

			//删除目录
			FileUtils.DeleteFolder(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/upload/"+request.getSession().getId());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}

