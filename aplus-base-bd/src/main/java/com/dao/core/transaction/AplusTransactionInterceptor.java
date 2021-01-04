package com.dao.core.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 16:11
 */
public class AplusTransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {
    private static final Object obj = new Object();

    private final ConcurrentMap<Object, PlatformTransactionManager> map = new ConcurrentReferenceHashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
        // TODO
        return invokeWithinTransaction(invocation.getMethod(),targetClass, (InvocationCallback) invocation.proceed());
    }

    @Override
    protected PlatformTransactionManager determineTransactionManager(TransactionAttribute txAttr) {
        if (txAttr == null || super.getBeanFactory() == null) {
            return (PlatformTransactionManager) getTransactionManager();
        }
        String qualifier = txAttr.getQualifier();
        if (StringUtils.hasText(qualifier)) {
            return determineQulifiedTransactionManager(super.getBeanFactory(), qualifier);
        } else if (StringUtils.hasText(super.getTransactionManagerBeanName())) {
            return determineQulifiedTransactionManager(super.getBeanFactory(), super.getTransactionManagerBeanName());
        } else if (AplusDataSourceHolder.get() != null) {
            // 增加通过数据源对象获取对应事务管理器的方法
            return getDataSourceTransactionManager((String) AplusDataSourceHolder.get());
        }else {
            PlatformTransactionManager defaultTransactionManager = (PlatformTransactionManager) getTransactionManager();
            if (defaultTransactionManager == null) {
                defaultTransactionManager  = this.map.get(obj);
                if (defaultTransactionManager == null) {
                   defaultTransactionManager =  super.getBeanFactory().getBean(PlatformTransactionManager.class);
                    map.putIfAbsent(obj, defaultTransactionManager);
                }
            }
            return defaultTransactionManager;
        }
    }

    private PlatformTransactionManager getDataSourceTransactionManager(String datasourceName) {
        PlatformTransactionManager manager = null;

        DataSource dataSource = super.getBeanFactory().getBean(datasourceName, DataSource.class);
        if (super.getBeanFactory() instanceof ListableBeanFactory) {
            Map<String, DataSourceTransactionManager> managerMap = ((ListableBeanFactory) super.getBeanFactory()).getBeansOfType(DataSourceTransactionManager.class);
            for (Map.Entry<String, DataSourceTransactionManager> entry : managerMap.entrySet()) {
                DataSourceTransactionManager value = entry.getValue();
                if (value instanceof DataSourceTransactionManager) {
                    DataSource ds = value.getDataSource();
                    if (ds == dataSource) {
                        manager = value;
                        break;
                    }
                }
            }
        }
        return manager;

    }

    private PlatformTransactionManager determineQulifiedTransactionManager(BeanFactory beanFactory, String qualifier) {
        PlatformTransactionManager manager = this.map.get(qualifier);
        if (manager == null) {
            manager = BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, PlatformTransactionManager.class, qualifier);
            this.map.putIfAbsent(qualifier, manager);
        }
        return manager;
    }

    @Override
    protected void clearTransactionManagerCache() {
        super.clearTransactionManagerCache();
    }
}
