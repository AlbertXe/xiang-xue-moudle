package com.dao.venus;

import com.dao.venus.event.ShardingDatasourceEvent;
import com.google.common.eventbus.Subscribe;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:20
 */
public class ShardingDataSourceBean extends AbstractDataSourceBean {

    public ShardingDataSourceBean(Map<String, DataSource> dataSourceMap) throws SQLException {
        super(dataSourceMap);
    }

    public ShardingDataSourceBean(String namespace) {
        super(namespace);
    }

    public ShardingDataSourceBean(String filePath, boolean local) {
        super(filePath, local);
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String filePath, boolean local) {
        ShardingDataSourceBuilder builder = new ShardingDataSourceBuilder();
        builder.build(filePath, local);
        return null;
    }

    @Override
    protected AbstractDataSourceAdapter createDataSource(String namespace) {
        ShardingDataSourceBuilder builder = new ShardingDataSourceBuilder();
       return (ShardingDataSource) builder.build(namespace);
    }

    /**
     * 订阅事件
     * @param datasourceEvent
     */
    @Subscribe
    public void renew(ShardingDatasourceEvent datasourceEvent) {
        if (this.getNamespace().equals(datasourceEvent.getNamespace())) {
            this.datasource.close();

            this.datasource = datasourceEvent.getShardingDataSource();
        }
    }
}
