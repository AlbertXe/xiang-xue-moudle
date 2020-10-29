package demo2.ch4;

import demo2.ch4.bean.Bird;
import demo2.ch4.config.MainConfig;
import demo2.ch4.config.MainConfig2;
import demo2.ch4.dao.TestDao;
import demo2.ch4.service.TestService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class Test7 {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        System.out.println("IOC init");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        Bird bean = (Bird) context.getBean("bird");
        System.out.println(bean);

        ConfigurableEnvironment environment = context.getEnvironment();
        String color = environment.getProperty("bird.color");
        System.out.println("environment==" + color);

    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);

        TestService service = context.getBean(TestService.class);
        service.printLn();

        TestDao testDao = context.getBean(TestDao.class);
        System.out.println(testDao);
    }
}
