package com.dao.junit;

import com.dao.core.DatasourceConfigProperties;
import com.dao.core.DatasourceModeProperties;
import com.dao.core.configuration.MybatisSqlSessionFactoryConfiguration;
import com.dao.listener.CleanupMybatisPageListenerRegister;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 11:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = JunitBean.class, value = "junit.enabled=true")
@SpringBootConfiguration
@Import({MybatisSqlSessionFactoryConfiguration.class, DatasourceConfigProperties.class,
        TransactionManagementConfigurer.class, CleanupMybatisPageListenerRegister.class,
        DataSourceAutoConfiguration.class, DatasourceModeProperties.class

})
public @interface JunitWithDataBase {
}
