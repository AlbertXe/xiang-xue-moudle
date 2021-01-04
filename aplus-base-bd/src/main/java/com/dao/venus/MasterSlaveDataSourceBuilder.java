package com.dao.venus;

import com.dao.venus.event.MasterSlaveDatasourceEvent;
import com.dao.venus.event.ShardingEventBusInstance;
import io.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 10:03
 */
public class MasterSlaveDataSourceBuilder extends AbstractDataSourceBuilder {

    @Override
    protected void refresh(DataSource ds, String namespace) {
        ShardingEventBusInstance.getInstance().post(new MasterSlaveDatasourceEvent(namespace, (MasterSlaveDataSource) ds));
    }

    @Override
    protected DataSource build(byte[] bytes) {
        MasterSlaveDataSource dataSource = null;
        try {
            dataSource = (MasterSlaveDataSource) YamlMasterSlaveDataSourceFactory.createDataSource(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
