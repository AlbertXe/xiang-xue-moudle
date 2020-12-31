package com.dao.core.configuration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.core.DatasourceConfigProperties;
import com.dao.core.InterceptorProperties;
import com.dao.core.enums.EnumTypeDefine;
import com.dao.plugins.mybatis.interceptor.BoundDefaultValueInterceptor;
import com.dao.plugins.mybatis.interceptor.MybatisLogInterceptor;
import com.dao.plugins.mybatis.interceptor.PageInterceptor;
import com.dao.plugins.mybatis.support.AplusEnumTypeHandler;
import com.dao.plugins.mybatis.support.MysqlJsonHandler;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 09:34
 */
@Configuration
public class MybatisSqlSessionFactoryFactory {
    private static final String ENUMPACK = "com.dao";

    @Autowired
    DatasourceConfigProperties datasourceConfigProperties;
    @Autowired
    DataSource dataSource;
    @Autowired
    InterceptorProperties interceptorProperties;
    Object LOCK = new Object();

    private Set<Class<? extends EnumTypeDefine>> sets;

    @Bean
    @ConditionalOnMissingBean(SqlSessionFactory.class)
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        String mapper = datasourceConfigProperties.getMapper();
        String[] mappers = mapper.split(",");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> list = Lists.newArrayList();
        for (String m : mappers) {
            Resource resource = resolver.getResource(m);
            list.add(resource);
        }
        factoryBean.setMapperLocations(list.toArray(new Resource[]{}));

        factoryBean.setConfiguration(buildConfiguration());

        String handlers = datasourceConfigProperties.getTypeHandlers();
        if (StringUtils.isNotBlank(handlers)) {
            String[] hs = handlers.split(",");
            List<TypeHandler> handlerList = Arrays.stream(hs).map(t -> {
                try {
                    TypeHandler o = (TypeHandler) ClassUtils.forName(t, null).newInstance();
                    return o;
                } catch (Exception e) {
                }
                return null;
            }).collect(Collectors.toList());
            factoryBean.setTypeHandlers(handlerList.toArray(new TypeHandler[]{}));
        }

        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(pageInterceptor());
        interceptorList.add(mybatisLogInterceptor());
        interceptorList.add(boundDefaultValueInterceptor());
        String[] interceptors = datasourceConfigProperties.getInterceptors().split(",");
        Arrays.stream(interceptors).forEach(t->{
            try {
                Interceptor o = (Interceptor) ClassUtils.forName(t, null).newInstance();
                interceptorList.add(o);
            } catch (Exception e) {

            }
        });

        factoryBean.setPlugins(interceptorList.toArray(new Interceptor[]{}));
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Bean
    @ConditionalOnMissingBean(SqlSessionTemplate.class)
    public SqlSessionTemplate sqlSessionTemplate() {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
        return sqlSessionTemplate;
    }

    @Bean
    public BoundDefaultValueInterceptor boundDefaultValueInterceptor() {
        BoundDefaultValueInterceptor interceptor = new BoundDefaultValueInterceptor();
        interceptor.setEnabledDsu(true);
        return interceptor;
    }
    @Bean
    public MybatisLogInterceptor mybatisLogInterceptor() {
        MybatisLogInterceptor interceptor = new MybatisLogInterceptor();
        interceptor.setShowsql(true);
        return interceptor;
    }
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setDialectClass(datasourceConfigProperties.getDialect());
        return interceptor;
    }

    private org.apache.ibatis.session.Configuration buildConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setDefaultStatementTimeout(datasourceConfigProperties.getSqlTimeOut());
        synchronized (LOCK) {
            if (CollectionUtils.isEmpty(sets)) {
                Reflections reflections = new Reflections(ENUMPACK);
                sets = reflections.getSubTypesOf(EnumTypeDefine.class);
            }
        }
        for (Class<? extends EnumTypeDefine> cls : sets) {
            Class<Enum> enumCls = (Class<Enum>) ReflectionUtils.forName(cls.getName(), null);
            configuration.getTypeHandlerRegistry().register(enumCls, new AplusEnumTypeHandler(enumCls));
        }
        configuration.setCacheEnabled(true);
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);

        configuration.getTypeHandlerRegistry().register(JSONObject.class, JdbcType.VARCHAR, MysqlJsonHandler.class);
        configuration.getTypeHandlerRegistry().register(JSONArray.class, JdbcType.VARCHAR, MysqlJsonHandler.class);
        return configuration;
    }

    //TODO


}
