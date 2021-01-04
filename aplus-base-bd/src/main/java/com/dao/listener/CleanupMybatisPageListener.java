package com.dao.listener;

import com.dao.plugins.mybatis.interceptor.PageInterceptor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 11:33
 */
public class CleanupMybatisPageListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PageInterceptor.closePool();
    }
}
