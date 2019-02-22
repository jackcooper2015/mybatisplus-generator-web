package com.reapal.conf;

import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName DbColumnTypeExt.java
 * @Description TODO
 * @createTime 2019年02月22日 18:39:00
 */
public enum DbColumnTypeExt implements IColumnType {
    // java8 新时间类型
    OFFSET_DATE_TIME("OffsetDateTime", "java.time.OffsetDateTime");

    /**
     * 类型
     */
    private final String type;

    /**
     * 包路径
     */
    private final String pkg;

    DbColumnTypeExt(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getPkg() {
        return pkg;
    }
}
