package com.reapal.dao;

import com.reapal.model.TemplateSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Desc: jpa 模板集合信息
 * @author jackcooper
 */
@Repository
public interface TemplateSetDao extends JpaRepository<TemplateSet, Long> {

}
