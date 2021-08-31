package com.reapal.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.reapal.conf.CustomerVelocityTemplateEngine;
import com.reapal.conf.MySqlTypeConvertExt;
import com.reapal.dao.DbConfigDao;
import com.reapal.dao.TableStrategyConfigDao;
import com.reapal.dao.TemplateDao;
import com.reapal.model.*;
import com.reapal.service.CodeService;
import com.reapal.utils.FileUtils;
import com.reapal.utils.ZipFileUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;


@Controller
@RequestMapping
public class CodeController extends BaseController{

	@Autowired
	private CodeService codeService;

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
		DbConfig dbConfig = dbConfigDao.getOne(id);
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
		dbConfigDao.save(dbConfig);
		return respJson(0,"添加成功",true);
	}

	/**
	 * 显示Table列表
	 */
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(Model model,DbConfig dbConfig){
		dbConfigDao.delete(dbConfig.getId());
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
			DbConfig dbConfig = dbConfigDao.getOne(id);
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
		DbConfig dbConfig = dbConfigDao.getOne(id);
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
		DbConfig dbConfig = dbConfigDao.getOne(id);
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(tableName);
		tableInfo.setComments(comments);
		List<ColumnInfo> listItem = new ArrayList<ColumnInfo>();
		for(String remark:remarks){
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
		tableStrategyConfig.setDbId(id);
		tableStrategyConfig.setTableName(tableName);
		tableStrategyConfigDao.save(tableStrategyConfig);
		return respJson(0, "保存成功", true);
	}

	@GetMapping("/batchGenerate")
	public String batchGenerate(String jsonStr,Long id,HttpServletResponse response){
		TableStrategyConfig tableStrategyConfig = JSON.parseObject(jsonStr, TableStrategyConfig.class);
		DbConfig dbConfig = dbConfigDao.getOne(id);
		this.buildCodes(response, dbConfig, tableStrategyConfig);
		return null;
	}

	/**
	 * 生成代码
	 */
	@RequestMapping(value="/generate",method=RequestMethod.GET)
	public String generate(String tableName, Long id, HttpServletResponse response) throws IOException {
		DbConfig dbConfig = dbConfigDao.getOne(id);
        TableStrategyConfig tableStrategyConfig = tableStrategyConfigDao.findByDbIdAndTableName(id, tableName);
		this.buildCodes(response, dbConfig, tableStrategyConfig);
		return null;
	}

	/**
	 * 生成代码的核心逻辑
	 * @param response
	 * @param dbConfig
	 * @param tableStrategyConfig
	 */
	private void buildCodes(HttpServletResponse response, DbConfig dbConfig, TableStrategyConfig tableStrategyConfig) {
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String outPutDir = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/" + request.getSession().getId();
		gc.setOutputDir(outPutDir);
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor(tableStrategyConfig.getAuthor());
		gc.setEntityName(tableStrategyConfig.getEntityName());
		gc.setMapperName(tableStrategyConfig.getMapperName());
		gc.setXmlName(tableStrategyConfig.getXmlName());
		gc.setServiceName(tableStrategyConfig.getServiceName());
		gc.setServiceImplName(tableStrategyConfig.getServiceImplName());
		mpg.setGlobalConfig(gc);
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(dbConfig.getDriver().indexOf("mysql")>-1? DbType.MYSQL:DbType.ORACLE);
		dsc.setDriverName(dbConfig.getDriver());
		dsc.setUsername(dbConfig.getUsername());
		dsc.setPassword(dbConfig.getPassword());
		dsc.setUrl(dbConfig.getUrl());
        dsc.setTypeConvert(new MySqlTypeConvertExt()); //dateTime ---> OFFSET_DATE_TIME
		mpg.setDataSource(dsc);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		if(!StringUtils.isEmpty(tableStrategyConfig.getPrefix())) {
            strategy.setTablePrefix(tableStrategyConfig.getPrefix());
		}
		strategy.setInclude(tableStrategyConfig.getTableName().split(",")); // 需要生成的表
		strategy.setLogicDeleteFieldName("is_delete");
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 字段名生成策略
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
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
		pc.setModuleName(tableStrategyConfig.getModelName());
		pc.setEntity(tableStrategyConfig.getEntityPackage());
		pc.setService(tableStrategyConfig.getServicePackage());
		pc.setServiceImpl(tableStrategyConfig.getServiceImplPackage());
		pc.setMapper(tableStrategyConfig.getMapperPackage());
		pc.setController(tableStrategyConfig.getControllerPackage());

		mpg.setPackageInfo(pc);


		//匹配模板
		matchTemplateConfig(mpg, outPutDir,tableStrategyConfig);

		// 执行生成
		mpg.execute();
		//打包下载
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition","attachment; filename=src.zip");
		try {
			ZipFileUtils zip = new ZipFileUtils();
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			String fileName = request.getSession().getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"upload"+File.separator+request.getSession().getId();
			File ff = new File(fileName);
			if(!ff.exists()){
				ff.mkdirs();
			}
			zip.zip(ff,zos,"");
			zos.flush();
			zos.close();
			//删除目录
			FileUtils.DeleteFolder(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 匹配模板
	 * @param mpg
	 * @param outPutDir
	 * @param tableStrategyConfig
	 */
	private void matchTemplateConfig(AutoGenerator mpg, String outPutDir, TableStrategyConfig tableStrategyConfig) {
		//系统默认模板
		TemplateCusConf tcc = new TemplateCusConf();
		if(tableStrategyConfig.getTemplateSetId()!=null && tableStrategyConfig.getTemplateSetId()>0 ){
			mpg.setTemplateEngine(new CustomerVelocityTemplateEngine());
			//在tmp下生成临时文件当模板
			List<Template> templates = templateDao.findByTemplateSetId(tableStrategyConfig.getTemplateSetId());
			String path = request.getSession().getServletContext().getRealPath("/");
			for (Template t : templates) {
				String fpath = path + t.getTemplateSetId()+ File.separator + t.getTemplateName();
				if(FileUtil.exist(fpath)){
					//删掉旧文件，防止模板更新
					FileUtil.del(fpath);
				}
				//创建新文件
				File file = FileUtil.touch(fpath);
				FileUtil.writeString(t.getContent(), file, Charset.defaultCharset());
				switch (t.getTemplateName()){
					case "controller.java.vm":
						tcc.setControllerPath(fpath);
						break;
					case "entity.java.vm":
						tcc.setEntityPath(fpath);
						break;
					case "mapper.java.vm":
						tcc.setMapperJavaPath(fpath);
						break;
					case "mapper.xml.vm":
						tcc.setMapperXmlPath(fpath);
						break;
					case "page.html.vm":
						tcc.setPageHtmlPath(fpath);
						break;
					case "page.vue.vm":
						tcc.setPageVuePath(fpath);
						break;
					case "page.js.vm":
						tcc.setPageJsPath(fpath);
						break;
					case "service.java.vm":
						tcc.setServicePath(fpath);
						break;
					case "serviceImpl.java.vm":
						tcc.setServiceImplPath(fpath);
						break;
					default:
				}
			}
		}else{
			mpg.setTemplateEngine(new VelocityTemplateEngine());
		}
		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {

			void initFileOutConfigList(){
				FileOutConfig fpage = new FileOutConfig(tcc.getPageHtmlPath()) {
					@Override
					public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
						//设置文件名
						final String fname = tableInfo.getName().replaceAll("_", "-" ).toLowerCase();
						return outPutDir + File.separator + "resource" + File.separator + "templates" + File.separator + "views" + File.separator + fname + ".html";
					}
				};
				FileOutConfig vuePage = new FileOutConfig(tcc.getPageVuePath()) {
					@Override
					public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
						//设置文件名
						final String fname = tableInfo.getName().replaceAll("_", "-" ).toLowerCase();
						return outPutDir + File.separator + "resource" + File.separator + "templates" + File.separator + "views" + File.separator + fname + ".vue";
					}
				};
				FileOutConfig fjs = new FileOutConfig(tcc.getPageJsPath()) {
					@Override
					public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
						//设置文件名
						final String fname = tableInfo.getName().replaceAll("_", "-" ).toLowerCase();
						return outPutDir + File.separator + "resource" + File.separator + "static"+File.separator+"js" + File.separator + fname + ".js";
					}
				};
				List<FileOutConfig> list = new ArrayList<>(Collections.emptyList());
				list.add(fpage);
				list.add(fjs);
				list.add(vuePage);
				this.setFileOutConfigList(list);
			}

			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entityLombokModel",true);
				map.put("restControllerStyle",true);
				this.setMap(map);
				this.initFileOutConfigList();
			}
		};
		mpg.setCfg(cfg);
		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		TemplateConfig tc = new TemplateConfig();
		tc.setController(tcc.getControllerPath());
		tc.setEntity(tcc.getEntityPath());
		tc.setMapper(tcc.getMapperJavaPath());
		tc.setXml(tcc.getMapperXmlPath());
		tc.setService(tcc.getServicePath());
		tc.setServiceImpl(tcc.getServiceImplPath());
		mpg.setTemplate(tc);
	}

}

