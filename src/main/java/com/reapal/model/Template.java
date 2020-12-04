package com.reapal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Template  implements Serializable {

    private static final long serialVersionUID = 7912547981585291572L;
    /** 数据库 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long templateSetId;
    private String templateName;
    private String content;
    private String createUser;
    private String remark;
}
