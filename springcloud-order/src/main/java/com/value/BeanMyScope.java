package com.value;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 21:25
 */
public class BeanMyScope implements Scope {
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        System.out.println("BeanMyScope:>>>>>>>>>" + name);
        return objectFactory.getObject();
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
