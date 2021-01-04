package com.dao.core.configuration;

import com.dao.venus.DefaultDataSourceBean;
import com.dao.venus.ShardingDataSourceBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:14
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource.second", name = "enabled", havingValue = "true", matchIfMissing = false)

public class MultiDatasourceConfiguration {


    @Bean(name = "secondTransactionManager")
    DataSourceTransactionManager transactionManager(@Qualifier("multiDatasource") DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    @Bean(name = "multiDatasource",destroyMethod = "close")
    public DataSource multiDatasource(MultiDatasourceConfigProperties multiDatasourceConfigProperties) {
        DataSource dataSource = null;
        String namespace = multiDatasourceConfigProperties.getNamespace();
        if (multiDatasourceConfigProperties.isSharding()) {
            if (StringUtils.isBlank(namespace)) {
                dataSource = new ShardingDataSourceBean("/second_jdbc.yml", true);
            }else {
                dataSource = new ShardingDataSourceBean(namespace);
            }

        }else {
            if (StringUtils.isBlank(namespace)) {
                dataSource = new DefaultDataSourceBean("/second_jdbc.yml", true);
            }else {
                dataSource = new DefaultDataSourceBean(namespace);
            }
        }
        return dataSource;
    }
}
