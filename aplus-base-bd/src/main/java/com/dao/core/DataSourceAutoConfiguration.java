package com.dao.core;

import com.dao.venus.DefaultDataSourceBean;
import com.dao.venus.ShardingDataSourceBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 13:37
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
@Getter
@Setter
public class DataSourceAutoConfiguration {
    @Value("${aplus.base.db.sharding:false}")
    private boolean sharding;
    @Autowired
    private DatasourceModeProperties datasourceModeProperties;

    @Bean(name = "dataSource", destroyMethod = "close")
    @Primary
    public DataSource dataSource() {
        DataSource dataSource;
        String namespace = datasourceModeProperties.getNamespace();
        if (sharding) {
            if (StringUtils.isBlank(namespace)) {
                dataSource = new ShardingDataSourceBean("/jdbc.yml", true);
            }else {
                dataSource = new ShardingDataSourceBean(namespace);
            }
        }else {
            if (StringUtils.isBlank(namespace)) {
                dataSource = new ShardingDataSourceBean("/jdbc.yml", true);
            }else {
                dataSource = new DefaultDataSourceBean(namespace);
            }
        }
        return dataSource;

    }

}
