package demo2.ch2;

import demo2.ch2.config.MainConfig;
import demo2.ch2.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Testch2 {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);

        Object bean1 = context.getBean("monkey");
        Object bean2 = context.getBean("&monkey");
        System.out.println("bean jamesFacrotyBean的类型====" + bean1.getClass());
        System.out.println("bean jamesFacrotyBean的类型====" + bean2.getClass());
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }
}
