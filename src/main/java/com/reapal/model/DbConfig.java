package com.reapal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


import jakarta.persistence.*;
import java.io.Serializable;

/**
 * @author jackcooper
 */
@Entity
@Data
@Table(name = "db_config")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class DbConfig implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** 数据库 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dbName;
	private String url="";
	private String driver="";	//com.mysql.jdbc.Driver
	private String username="";
	private String password="";
	@Column(name = "schema_name")
	private String schema;
	@Column(name = "catalog_name")
	private String catalog;
	private String dbType;


}

