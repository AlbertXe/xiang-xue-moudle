package com.value;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 20:02
 */
public class ValueTest {
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DbConfigImport.class);
        context.refresh();

        DbConfig bean = context.getBean(DbConfig.class);
        System.out.println(bean);
    }

    /**
     * 测试 @Configurable
     */
    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DbConfigImport.class);
        context.refresh();

        BService bService = new BService();
        bService.get();
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 模拟数据
        Map<String, Object> map = new HashMap<>();
        map.put("mail.host", "smtp.qq.com");
        map.put("mail.username", "路人");
        map.put("mail.password", "123");

        MapPropertySource propertySource = new MapPropertySource("mail", map);
        // 将数据加入缓存
        context.getEnvironment().getPropertySources().addFirst(propertySource);
        context.register(DbConfigImport.class);
        context.refresh();

        MailConfig bean = context.getBean(MailConfig.class);
        System.out.println(bean);

    }
}
