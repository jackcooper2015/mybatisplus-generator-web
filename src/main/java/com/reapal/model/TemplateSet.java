package com.reapal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class TemplateSet implements Serializable {

    private static final long serialVersionUID = 3921376649827550473L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String setName;
    @jakarta.persistence.Column(name = "description")
    private String desc;
    private String createUser;

}
