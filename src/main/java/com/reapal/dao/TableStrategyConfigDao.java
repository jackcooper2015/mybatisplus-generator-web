package com.reapal.dao;

import com.reapal.model.TableStrategyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Desc: jpa 保存数据库链接信息
 * @author jackcooper
 */
@Repository
public interface TableStrategyConfigDao extends JpaRepository<TableStrategyConfig, Long> {
    public TableStrategyConfig findByDbIdAndTableName(Long dbId,String tableName);
}
