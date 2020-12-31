package com.dao.venus;


import com.dao.venus.event.ShardingEventBusInstance;
import groovy.transform.SelfType;
import io.shardingsphere.core.constant.DatabaseType;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import io.shardingsphere.transaction.core.datasource.ShardingTransactionalDataSource;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 关键的连接JDBC类
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:22
 */
@Getter
@Setter
public abstract class AbstractDataSourceBean extends AbstractDataSourceAdapter {
    protected AbstractDataSourceAdapter datasource;
    protected String namespace;


    public AbstractDataSourceBean(Map<String, DataSource> dataSourceMap) throws SQLException {
        super(dataSourceMap);
        ShardingEventBusInstance.getInstance().register(this);
    }

    public AbstractDataSourceBean(String namespace) {
        this.namespace = namespace;
        this.datasource = createDataSource(namespace);
        ShardingEventBusInstance.getInstance().register(this);
    }

    public AbstractDataSourceBean(String filePath, boolean local) {
        this.datasource = createDataSource(filePath,local);
        this.namespace = local ? null : filePath;
        ShardingEventBusInstance.getInstance().register(this);
    }

    @Override
    public void close() {
        datasource.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return datasource.getDatabaseType();
    }

    @Override
    public ShardingTransactionalDataSource getShardingTransactionalDataSources() {
        return super.getShardingTransactionalDataSources();
    }

    protected abstract AbstractDataSourceAdapter createDataSource(String filePath, boolean local);

    protected abstract AbstractDataSourceAdapter createDataSource(String namespace);
}
