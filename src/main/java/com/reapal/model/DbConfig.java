package com.reapal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author jackcooper
 */
@Entity
@Data
@Table(name = "db_config")
public class DbConfig implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** 数据库 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String dbName;
	private String url="";
	private String driver="";	//com.mysql.jdbc.Driver
	private String username="";
	private String password="";
	private String schema;
	private String catalog;
	private String dbType;


}

