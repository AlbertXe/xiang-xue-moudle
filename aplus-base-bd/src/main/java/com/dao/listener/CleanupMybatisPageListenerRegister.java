package com.dao.listener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**
 * 监听注册器
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 11:30
 */
@Configuration
public class CleanupMybatisPageListenerRegister {

    /**
     * 注册一个: filterRegistrationBean 添加请求过滤规则
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<EventListener> registerListener() {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean(new CleanupMybatisPageListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
