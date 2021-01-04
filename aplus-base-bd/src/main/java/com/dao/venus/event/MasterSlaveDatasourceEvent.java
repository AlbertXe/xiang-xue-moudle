package com.dao.venus.event;

import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 09:27
 */
@Getter
@Setter
public class MasterSlaveDatasourceEvent {
    private String namespace;
    private MasterSlaveDataSource masterSlaveDataSource;

    public MasterSlaveDatasourceEvent(String namespace, MasterSlaveDataSource masterSlaveDataSource) {
        this.namespace = namespace;
        this.masterSlaveDataSource = masterSlaveDataSource;
    }
}
