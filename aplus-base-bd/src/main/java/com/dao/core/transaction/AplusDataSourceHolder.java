package com.dao.core.transaction;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 17:07
 */
public final class AplusDataSourceHolder {
    private static ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();

    public static Object get() {
        return threadLocal.get();
    }

    public static void set(Object object) {
        threadLocal.set(object);

    }

    public static void remove() {
        threadLocal.remove();
    }
}
