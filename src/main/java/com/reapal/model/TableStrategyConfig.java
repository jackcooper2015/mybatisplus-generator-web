package com.reapal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ClassName :
 * @Author : jack-cooper
 * @Description:
 * @Date : 2018-09-20 18:21
 */
@Entity
@Data
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class TableStrategyConfig implements Serializable {

    private static final long serialVersionUID = 2560431775903963939L;
    @Id
    private Long id;
    private Long dbId;
    private String tableName;
    private String prefix;
    private String modelName;
    private String author;
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String entityPackage;
    private String servicePackage;
    private String serviceImplPackage;
    private String mapperPackage;
    private String controllerPackage;

}
