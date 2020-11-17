package com.value;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-17 10:50
 */
public class BeanRefreshScope implements Scope {
    private static final BeanRefreshScope instance = new BeanRefreshScope();
    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

    private BeanRefreshScope() {

    }

    public static BeanRefreshScope getInstance() {
        return instance;
    }

    public void clean() {
        instance.beanMap.clear();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            bean = objectFactory.getObject();
            beanMap.put(name, bean);
        }
        return bean;
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
