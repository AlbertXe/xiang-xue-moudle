package com.dao.venus.event;

import com.dao.venus.AbstractDataSourceBean;
import com.dao.venus.MasterSlaveDataSourceBuilder;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 10:01
 */
public class MasterSlaveDataSourceBean extends AbstractDataSourceBean {
    public MasterSlaveDataSourceBean(Map<String, DataSource> dataSourceMap) throws SQLException {
        super(dataSourceMap);
    }

    public MasterSlaveDataSourceBean(String namespace) {
        super(namespace);
    }

    public MasterSlaveDataSourceBean(String filePath, boolean local) {
        super(filePath, local);
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String filePath, boolean local) {
        MasterSlaveDataSourceBuilder builder = new MasterSlaveDataSourceBuilder();
        return null;
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String namespace) {
        return null;
    }


}
