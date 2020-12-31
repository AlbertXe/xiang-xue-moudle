package com.dao.venus;

import com.google.common.base.Preconditions;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 17:25
 */
@Getter
@Setter
public class DefaultDataSource extends AbstractDataSourceAdapter implements AutoCloseable {
    private DataSource dataSource;

    public DefaultDataSource(Map<String, DataSource> dataSources) throws SQLException {
        super(dataSources);
        checkDataSourceType(dataSources);
        this.dataSource = getDataSource(dataSources);
    }

    private DataSource getDataSource(Map<String, DataSource> dataSources) {
        if (MapUtils.isNotEmpty(dataSources)) {
            DataSource dataSource = dataSources.values().stream().findFirst().get();
            return dataSource;
        }else {
            return null;
        }

    }

    private void checkDataSourceType(Map<String, DataSource> dataSources) {
        dataSources.forEach((k,v)->{
            Preconditions.checkArgument(!(v instanceof MasterSlaveDataSource), "can not be master slave datasource");
        });
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
