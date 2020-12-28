package com.dao.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:11
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.second",ignoreInvalidFields = true)
@Getter
@Setter
public class MultiDatasourceConfigProperties {
    public static final String LOCALDB = "/second_jdbc.yml";

    /**
     * mapper.xml路径
     */
    private String namespace;
    private boolean sharding =false;
    private boolean enabled;
    private String packages;



}
