package com.dao.core.configuration;

import com.dao.core.dao.AbstractEntityDaoImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:09
 */
public class MultiApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    MultiDatasourceConfigProperties multiDatasourceConfigProperties;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (!multiDatasourceConfigProperties.isEnabled()) return ;
        String[] packs = multiDatasourceConfigProperties.getPackages().split(",");
        List<String> pkList = Lists.newArrayList(packs);

        Map<String, AbstractEntityDaoImpl> map = applicationContext.getBeansOfType(AbstractEntityDaoImpl.class);
        map.forEach((k,v)->{
            if (pkList.contains(v.getClass().getPackage().getName())) {
                v.setSqlSessionTemplate(MultiMybatisSessionFactory.sqlSessionTemplate());
            }
        });



    }
}
