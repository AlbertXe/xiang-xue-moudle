package com.dao.venus;

import com.dao.venus.event.ShardingDatasourceEvent;
import com.dao.venus.event.ShardingEventBusInstance;
import io.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 16:32
 */
public class ShardingDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected void refresh(DataSource ds, String namespace) {
        ShardingEventBusInstance.getInstance().post(new ShardingDatasourceEvent(namespace, (ShardingDataSource) ds));
    }

    @Override
    protected DataSource build(byte[] bytes) {
        try {
            return YamlShardingDataSourceFactory.createDataSource(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
