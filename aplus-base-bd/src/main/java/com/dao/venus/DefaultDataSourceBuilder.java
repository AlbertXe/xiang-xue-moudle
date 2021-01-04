package com.dao.venus;

import com.dao.venus.event.DefaultDatasourceEvent;
import com.dao.venus.event.ShardingEventBusInstance;
import io.shardingsphere.core.yaml.sharding.YamlShardingConfiguration;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 17:20
 */
public class DefaultDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected void refresh(DataSource ds, String namespace) {
        DefaultDataSource defaultDataSource = (DefaultDataSource) ds;
        ShardingEventBusInstance.getInstance().post(new DefaultDatasourceEvent(namespace, (DefaultDataSource) ds));

    }

    @Override
    protected DataSource build(byte[] bytes) {
        YamlShardingConfiguration config = null;
        try {
            config = YamlShardingConfiguration.unmarshal(bytes);
            DefaultDataSource dataSource = new DefaultDataSource(config.getDataSources());
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
