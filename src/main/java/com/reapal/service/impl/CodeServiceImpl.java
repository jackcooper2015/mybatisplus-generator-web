package com.reapal.service.impl;

import com.reapal.dao.CodeDao;
import com.reapal.model.DbConfig;
import com.reapal.model.TableInfo;
import com.reapal.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 10400
 */
@Service
public class CodeServiceImpl implements CodeService {
	@Autowired
	private CodeDao codeDao;
	
	@Override
	public List<TableInfo> getAllTables(DbConfig dbConfig){
		return codeDao.getAllTables(dbConfig);
	}
	
	@Override
	public TableInfo getAllColumns(String tableName, DbConfig dbConfig){
		return codeDao.getAllColumns(tableName,dbConfig);
	}

	@Override
	public void saveComment(TableInfo tableInfo, DbConfig dbConfig){
		codeDao.saveComment(tableInfo,dbConfig);
	}

	@Override
	public String testConnection(DbConfig dbConfig){
		return codeDao.testConnection(dbConfig);
	}

}
