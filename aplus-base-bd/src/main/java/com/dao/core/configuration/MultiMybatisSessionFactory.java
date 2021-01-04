package com.dao.core.configuration;

import com.dao.core.DatasourceConfigProperties;
import com.dao.plugins.mybatis.interceptor.BoundDefaultValueInterceptor;
import com.dao.plugins.mybatis.interceptor.MybatisLogInterceptor;
import com.dao.plugins.mybatis.interceptor.PageInterceptor;
import com.dao.util.SpringContextUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:54
 */
@Slf4j
public class MultiMybatisSessionFactory {
    private static SqlSessionTemplate sqlSessionTemplate;

    private static Object LOCK = new Object();

    static SqlSessionTemplate sqlSessionTemplate() {
        synchronized (LOCK) {
            if (sqlSessionTemplate == null) {
                sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
            }
            return sqlSessionTemplate;
        }
    }

    private static SqlSessionFactory sqlSessionFactory() {
        MybatisSqlSessionFactoryConfiguration mybatisSqlSessionFactoryConfiguration = SpringContextUtil.getBean(MybatisSqlSessionFactoryConfiguration.class);
        DatasourceConfigProperties datasourceConfigProperties = SpringContextUtil.getBean(DatasourceConfigProperties.class);
        String mapper = datasourceConfigProperties.getMapper();

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(SpringContextUtil.getBean("multiDataSource", DataSource.class));

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList = Lists.newArrayList();
        Arrays.stream(mapper.split(",")).forEach(t->{
                    try {
                        Resource[] resources = resolver.getResources(t);
                        resourceList.addAll(Arrays.asList(resources));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        );
        factoryBean.setMapperLocations(resourceList.toArray(new Resource[]{}));

        factoryBean.setConfiguration(mybatisSqlSessionFactoryConfiguration.buildConfiguration());

        String typeHandlers = datasourceConfigProperties.getTypeHandlers();
        List<TypeHandler> typeHandlerList = Lists.newArrayList();
        Arrays.stream(typeHandlers.split(",")).forEach(t->{
            try {
                typeHandlerList.add((TypeHandler) ClassUtils.forName(t, null).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        factoryBean.setTypeHandlers(typeHandlerList.toArray(new TypeHandler[]{}));


        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.add(SpringContextUtil.getBean(PageInterceptor.class));
        interceptorList.add(SpringContextUtil.getBean(BoundDefaultValueInterceptor.class));
        interceptorList.add(SpringContextUtil.getBean(MybatisLogInterceptor.class));
        String interceptors = datasourceConfigProperties.getInterceptors();
        Arrays.stream(interceptors.split(",")).forEach(t->{
            try {
                interceptorList.add((Interceptor) ClassUtils.forName(t, null).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
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
}
