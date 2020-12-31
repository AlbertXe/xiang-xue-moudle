package com.dao.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 09:59
 */
@Configuration
@ConfigurationProperties(prefix = "spring.interceptor", ignoreInvalidFields = true)
@Getter
@Setter
public class InterceptorProperties {
    private boolean enabledDsu = true;

}
