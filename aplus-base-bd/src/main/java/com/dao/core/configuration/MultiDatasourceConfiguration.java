package com.dao.core.configuration;

import com.dao.venus.ShardingDataSourceBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 15:14
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource.second", name = "enabled", havingValue = "true", matchIfMissing = false)

public class MultiDatasourceConfiguration {

    @Bean(name = "multiDatasource",destroyMethod = "close")
    public DataSource multiDatasource(MultiDatasourceConfigProperties multiDatasourceConfigProperties) {
        DataSource dataSource = null;
        if (multiDatasourceConfigProperties.isSharding()) {
            if (StringUtils.isBlank(multiDatasourceConfigProperties.getNamespace())) {
                dataSource = new ShardingDataSourceBean("/second_jdbc.yml", true);
            }else {

            }

        }else {

        }

    }
}
