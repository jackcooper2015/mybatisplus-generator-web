package com.reapal.model;

import java.io.Serializable;
import java.util.List;

public class TableInfo implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	private String tableName;
	private String comments;
	private List<ColumnInfo> listColumn;

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<ColumnInfo> getListColumn() {
		return listColumn;
	}
	public void setListColumn(List<ColumnInfo> listColumn) {
		this.listColumn = listColumn;
	}
	
	
}

