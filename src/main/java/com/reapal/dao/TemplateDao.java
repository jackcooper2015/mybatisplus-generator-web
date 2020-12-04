package com.reapal.dao;

import com.reapal.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Desc: jpa 模板信息
 * @author jackcooper
 */
@Repository
public interface TemplateDao extends JpaRepository<Template, Long> {

    List<Template> findByTemplateSetId(Long setId);

}
