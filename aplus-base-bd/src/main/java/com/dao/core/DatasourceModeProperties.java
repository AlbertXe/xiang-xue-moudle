package com.dao.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 11:38
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource",ignoreInvalidFields = true)
@ConditionalOnProperty(prefix = "aplus.base.db", name = "enabled", havingValue = "true", matchIfMissing = true)
@Getter
@Setter
public class DatasourceModeProperties {
    public static final String LOCAL_DB = "/jdbc.yml";
    private String namespace;

}
