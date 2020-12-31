package com.dao.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 09:38
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }
    public static <T> T getBean(String name,Class<T> cls) {
        return applicationContext.getBean(name,cls);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static <T> T getBeanIfPresent(Class<T> cls) {
        Map<String, T> map = applicationContext.getBeansOfType(cls);
        return map != null && !map.isEmpty() ? applicationContext.getBean(cls):null;
    }


}
