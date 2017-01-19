package com.reapal.service;


import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.reapal.model.CodeConfig;
import com.reapal.model.DbConfig;
import com.reapal.model.TableInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeService {

	public List<TableInfo> getAllTables(DbConfig dbConfig);
	
	public TableInfo getAllColumns(String tableName, DbConfig dbConfig);
	
	public void saveComment(TableInfo tableInfo, DbConfig dbConfig);

}