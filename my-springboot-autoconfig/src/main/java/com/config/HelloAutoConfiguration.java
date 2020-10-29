package com.config;

import com.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-10-29 10:58
 */
@Configuration
@EnableConfigurationProperties(HelloConfigProperties.class)
@ConditionalOnProperty(prefix = "hello", name = "enable", havingValue = "false")
public class HelloAutoConfiguration {
    @Autowired
    private HelloConfigProperties helloConfig;


    @Bean
    @ConditionalOnMissingBean(HelloService.class)
    public HelloService helloService() {
        HelloService helloService = new HelloService();
        helloService.setMsg(helloConfig.getMsg());
        return helloService;
    }
}
