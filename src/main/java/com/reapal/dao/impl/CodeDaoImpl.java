package com.reapal.dao.impl;

import com.reapal.dao.CodeDao;
import com.reapal.model.ColumnInfo;
import com.reapal.model.DbConfig;
import com.reapal.model.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CodeDaoImpl implements CodeDao {


	@Override
	public void saveComment(TableInfo tableInfo, DbConfig dbConfig){
		Connection conn = getConnection(dbConfig);
	    try{
	    	Statement stmt = conn.createStatement();
	    	String strSql = "";

	    	if(dbConfig.getUrl().indexOf("mysql")>0){
	    		strSql = "ALTER TABLE "+tableInfo.getTableName()+" COMMENT '"+tableInfo.getComments()+"';";
	    		stmt.executeUpdate(strSql);
		    	//stmt.executeUpdate("use information_schema;");
		    	for(ColumnInfo item : tableInfo.getListColumn()){
					StringBuilder sb = new StringBuilder();
					sb.append("ALTER TABLE ");
					if(StringUtils.isEmpty(tableInfo.getTableName())){
						continue;
					}else{
						sb.append(" "+tableInfo.getTableName()+" ");
					}
					sb.append(" MODIFY ");
					if(StringUtils.isEmpty(item.getColName())){
						continue;
					}else{
						sb.append(" `"+item.getColName()+"` ");
					}
					sb.append(" "+ item.getColType()+" ");
					if(!item.isNullable()){
						sb.append(" NOT NULL ");
					}
					if(!StringUtils.isEmpty(item.getDefaultValue())){
						if(item.getDefaultValue().equalsIgnoreCase("undefined")) {
							sb.append(" DEFAULT '" + item.getDefaultValue() + "' ");
						}else{
							sb.append(" DEFAULT " + item.getDefaultValue() + " ");
						}
					}
					if(!StringUtils.isEmpty(item.getExtra())){
						sb.append(" " + item.getExtra() + " ");
					}
					if(!StringUtils.isEmpty(item.getComments())){
						sb.append(" COMMENT '" + item.getComments() + "' ;");
					}
					log.info("update mysql column, sql is : {}",sb.toString());
		    		stmt.executeUpdate(sb.toString());
		    	}
	    	} else{
	    		strSql = "COMMENT ON TABLE "+tableInfo.getTableName()+" IS '#"+tableInfo.getComments()+"'";
	    		stmt.executeUpdate(strSql);
		    	for(ColumnInfo item : tableInfo.getListColumn()){
		    		strSql = "COMMENT ON COLUMN "+tableInfo.getTableName()+"."+item.getColName()+" IS '"+item.getComments()+"'";
		    		stmt.executeUpdate(strSql);
		    	}
	    	}
	    	if(stmt != null){   // 关闭声明
		        try{
		            stmt.close() ;
		        }catch(SQLException e){
		            e.printStackTrace() ;
		        }
	    	}
	    }
	    catch(SQLException e)
	    {
	      throw new RuntimeException("execute sql occer error", e);
	    }
	    finally{
	      try{
	        conn.close();
	      }catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	    }
	};

	@Override
	public List<TableInfo> getAllTables(DbConfig dbConfig){
		List<TableInfo> tableList = new ArrayList<TableInfo>();

		Connection conn = getConnection(dbConfig);
	    try{
	    	Statement stmt = conn.createStatement();
	    	String strSql = "";
	    	if(dbConfig.getUrl().indexOf("mysql")>0){
	    		strSql = "select table_name,TABLE_COMMENT from information_schema.tables where table_schema='"+dbConfig.getSchema()+"'";
	    	}
	    	else{
	    		strSql = "select table_name,comments from user_tab_comments where table_type='TABLE' order by table_name";
	    	}
	    	System.out.println(">>>>>>>>>>>>" + strSql);
	    	ResultSet rs = stmt.executeQuery(strSql);
	    	while(rs.next()){
	    		TableInfo table = new TableInfo();
	    		table.setTableName(rs.getString(1));
	    		table.setComments(rs.getString(2));
	    		tableList.add(table);
	        }

	    	if(stmt != null){   // 关闭声明
		        try{
		            stmt.close() ;
		        }catch(SQLException e){
		            e.printStackTrace() ;
		        }
	    	}
	    }
	    catch(SQLException e)
	    {
	      throw new RuntimeException("execute sql occer error", e);
	    }
	    finally{
	      try{
	        conn.close();
	      }catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	    }

		return tableList;
	}

	@Override
	public TableInfo getAllColumns(String tableName, DbConfig dbConfig){
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(tableName);

		Connection conn = getConnection(dbConfig);
	    try{
	    	Statement stmt = conn.createStatement();
	    	String strSql = "";
	    	//得到表注解
	    	if(dbConfig.getUrl().indexOf("mysql")>0){
	    		strSql = "select TABLE_COMMENT from information_schema.tables where table_name='"+tableName+"' and table_schema='"+dbConfig.getSchema()+"'";
	    	}
	    	else{
	    		strSql = "select comments from user_tab_comments where table_name='"+tableName+"'";
	    	}
	    	ResultSet rs = stmt.executeQuery(strSql);
	    	while(rs.next()){
	    		tableInfo.setComments(rs.getString(1));
	        }

	    	//得到字段注解
	    	if(dbConfig.getUrl().indexOf("mysql")>0){
	    		strSql = "select column_name,column_comment,column_type,is_nullable,extra,column_default from Information_schema.columns where table_Name = '"+tableName+"' and table_schema='"+dbConfig.getSchema()+"'";
			}
	    	else{
	    		strSql = "select z.COLUMN_NAME,c.comments,z.data_type from user_tab_columns z,user_col_comments c where z.TABLE_NAME=c.table_name and z.COLUMN_NAME=c.column_name and z.Table_Name='"+tableName+"'";
	    	}
	    	List<ColumnInfo> colList = new ArrayList<>();
	    	rs = stmt.executeQuery(strSql);
	    	while(rs.next()){
	    		ColumnInfo colInfo = new ColumnInfo();
	    		colInfo.setColName(rs.getString(1));
				colInfo.setComments(rs.getString(2));
				if(dbConfig.getUrl().indexOf("mysql")>0) {
					colInfo.setColType(rs.getString(3));
					colInfo.setDefaultValue(rs.getString(6));
					colInfo.setNullable("YES".equals(rs.getString(4)));
				}else{
					colInfo.setColType(rs.getString(3));
				}
				if (!StringUtils.isEmpty(rs.getString(5))) {
					colInfo.setExtra(rs.getString(5));
				} else {
					colInfo.setExtra("");
				}
	    		colList.add(colInfo);
	        }
	    	tableInfo.setListColumn(colList);

	    	if(stmt != null){   // 关闭声明
		        try{
		            stmt.close() ;
		        }catch(SQLException e){
		            e.printStackTrace() ;
		        }
	    	}
	    }
	    catch(SQLException e)
	    {
	      throw new RuntimeException("execute sql occer error", e);
	    }
	    finally{
	      try{
	        conn.close();
	      }catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	    }

		return tableInfo;
	}

	/* 获取数据库连接的函数*/
    private Connection getConnection(DbConfig dbConfig) {
        Connection con = null;  //创建用于连接数据库的Connection对象
        try {
            Class.forName(dbConfig.getDriver());// 加载Mysql数据驱动
            con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());// 创建数据连接

        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; //返回所建立的数据库连接
    }

	/* 获取数据库连接的函数*/
	@Override
	public String testConnection(DbConfig dbConfig) {
		Connection con = null;  //创建用于连接数据库的Connection对象
		try {
			Class.forName(dbConfig.getDriver());// 加载Mysql数据驱动
			con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());// 创建数据连接
			if(con == null){
				return "数据库连接失败";
			}
		} catch (Exception e) {
			System.out.println("数据库连接失败：" + e.getMessage());
			return e.getMessage();
		}
		return null;
	}


}
