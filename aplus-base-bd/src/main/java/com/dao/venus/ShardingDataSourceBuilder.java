package com.dao.venus;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 16:32
 */
public class ShardingDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected void refresh(DataSource ds, String namespace) {

    }

    @Override
    protected DataSource build(byte[] bytes) {
        return null;
    }

    public void build(String filePath, boolean local) {

    }
}
