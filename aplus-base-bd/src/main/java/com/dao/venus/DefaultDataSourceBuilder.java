package com.dao.venus;

import io.shardingsphere.core.yaml.sharding.YamlShardingConfiguration;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 17:20
 */
public class DefaultDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected void refresh(DataSource ds, String namespace) {
    }

    @Override
    protected DataSource build(byte[] bytes) throws IOException, SQLException {
        YamlShardingConfiguration config = YamlShardingConfiguration.unmarshal(bytes);
        DefaultDataSource dataSource = new DefaultDataSource(config.getDataSources());
        return dataSource;
    }
}
