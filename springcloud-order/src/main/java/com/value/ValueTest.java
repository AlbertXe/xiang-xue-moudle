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

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope("my", new BeanMyScope());
        context.register(MainConfig3.class);
        context.refresh();

        User user = context.getBean(User.class);
        System.out.println("user class:" + user.getClass());

        System.out.println("多次调用");
        for (int i = 0; i < 3; i++) {
            System.out.println("user class:" + user.getClass());
            // 每次调用user.getUsername方法的时候，内部自动调用了BeanMyScope#get 方法和 User的构造函数

            //当自定义的Scope中proxyMode=ScopedProxyMode.TARGET_CLASS的时候，会给这个bean创建一个代理对象，调用代理对象的任何方法，
            // 都会调用这个自定义的作用域实现类（上面的BeanMyScope）中get方法来重新来获取这个bean对象。
            System.out.println("username============>" + user.getName());

        }
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope("refresh", BeanRefreshScope.getInstance());
        context.register(MainConfig4.class);
        RefreshConfigUtil.refreshConfig(context);
        context.refresh();

        MailConfig bean = context.getBean(MailConfig.class);

        for (int i = 0; i < 2; i++) {
            System.out.println(bean.toString());
        }

        for (int i = 0; i < 2; i++) {
            // 更新配置
            RefreshConfigUtil.updateConfig(context);
            System.out.println(bean);
        }

    }


}
