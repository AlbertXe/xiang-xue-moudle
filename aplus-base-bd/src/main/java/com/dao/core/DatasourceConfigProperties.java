package com.dao.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 主数据源属性
 *
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 17:22
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource", ignoreInvalidFields = true)
public class DatasourceConfigProperties {
    private String mapper = "classpath*:com/**/mapper/**/*Mapper*.xml";
    private boolean showSql = true;
    private String dialect = "";
    // 拦截器
    private String interceptors;
    private String typeHandlers;
    private int sqlTimeOut;

}
