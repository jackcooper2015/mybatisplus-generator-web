package com.reapal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class TemplateSet implements Serializable {

    private static final long serialVersionUID = 3921376649827550473L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String setName;
    private String desc;
    private String createUser;

}
