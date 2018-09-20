package com.reapal.dao;

import com.reapal.model.DbConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Desc: jpa 保存数据库链接信息
 * @author jackcooper
 */
@Repository
public interface DbConfigDao extends JpaRepository<DbConfig, Long> {

}
