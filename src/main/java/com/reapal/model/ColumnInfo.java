package com.reapal.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ColumnInfo implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/**
	 * 列名
	 */
	private String colName;
	/**
	 * 列类型
	 */
	private String colType;
	/**
	 * 列备注
	 */
	private String comments;
	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 是否允许为null
	 */
	private boolean isNullable;
	/**
	 * 其他
	 */
	private String extra;


	
}

