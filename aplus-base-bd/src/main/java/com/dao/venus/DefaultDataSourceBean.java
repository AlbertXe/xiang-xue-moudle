package com.dao.venus;

import com.dao.venus.event.DefaultDatasourceEvent;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:20
 */
public class DefaultDataSourceBean extends AbstractDataSourceBean {

    public DefaultDataSourceBean(Map<String, DataSource> dataSourceMap) throws SQLException {
        super(dataSourceMap);
    }

    public DefaultDataSourceBean(String namespace) {
        super(namespace);
    }

    public DefaultDataSourceBean(String filePath, boolean local) {
        super(filePath, local);
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String filePath, boolean local) {
        ShardingDataSourceBuilder builder = new ShardingDataSourceBuilder();
        return (DefaultDataSource) builder.build(filePath, local);
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String namespace) {
        return null;
    }

    public void renew(DefaultDatasourceEvent datasourceEvent) {
        if (this.getNamespace().equals(datasourceEvent.getNamespace())) {
            this.datasource.close();
            this.datasource = datasourceEvent.getDefaultDataSource();
        }
    }
}
