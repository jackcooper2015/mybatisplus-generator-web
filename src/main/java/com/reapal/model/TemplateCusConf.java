package com.reapal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackcooper
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateCusConf {
    String controllerPath = "/templates/gen-template/controller.java.vm";
    String entityPath = "/templates/gen-template/entity.java.vm";
    String mapperJavaPath = "/templates/gen-template/mapper.java.vm";
    String mapperXmlPath = "/templates/gen-template/mapper.xml.vm";
    String pageHtmlPath = "/templates/gen-template/page.html.vm";
    String pageVuePath = "/templates/gen-template/page.vue.vm";
    String pageJsPath = "/templates/gen-template/page.js.vm";
    String servicePath = "/templates/gen-template/service.java.vm";
    String serviceImplPath = "/templates/gen-template/serviceImpl.java.vm";
}
