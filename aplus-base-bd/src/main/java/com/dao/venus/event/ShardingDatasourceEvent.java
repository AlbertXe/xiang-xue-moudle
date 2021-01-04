package com.dao.venus.event;

import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 09:27
 */
@Getter
@Setter
public class ShardingDatasourceEvent {
    private String namespace;
    private ShardingDataSource shardingDataSource;

    public ShardingDatasourceEvent(String namespace, ShardingDataSource shardingDataSource) {
        this.namespace = namespace;
        this.shardingDataSource = shardingDataSource;
    }
}
