package demo2.ch1;

import demo2.ch1.config.MainConfig3;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap3Test2 {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);

        String[] names = context.getBeanDefinitionNames();//调用bean定义
        for (String name : names) {
            System.out.println(name);
        }

        Object bean1 = context.getBean("person");
        Object bean2 = context.getBean("person");
        System.out.println(bean1 == bean2);

    }
}
