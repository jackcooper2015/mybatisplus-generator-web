package com.reapal.service.impl;

import com.reapal.dao.CodeDao;
import com.reapal.model.DbConfig;
import com.reapal.model.TableInfo;
import com.reapal.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CodeServiceImpl implements CodeService {
	@Autowired
	private CodeDao codeDao;
	
	public List<TableInfo> getAllTables(DbConfig dbConfig){
		return codeDao.getAllTables(dbConfig);
	}
	
	public TableInfo getAllColumns(String tableName,DbConfig dbConfig){
		return codeDao.getAllColumns(tableName,dbConfig);
	}
	

	
	public void saveComment(TableInfo tableInfo,DbConfig dbConfig){
		codeDao.saveComment(tableInfo,dbConfig);
	}

	public String testConnection(DbConfig dbConfig){
		return codeDao.testConnection(dbConfig);
	}

}
