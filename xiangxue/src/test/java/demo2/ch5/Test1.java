package demo2.ch5;

import demo2.ch5.aop.Caculator;
import demo2.ch5.config.MainConfig;
import demo2.ch5.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test1 {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        System.out.println(context);
    }

    @Test
    public void test02() {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(MainConfig2.class);

        Caculator bean = app.getBean(Caculator.class);
        int i = bean.div(4, 3);
        System.out.println(i);
    }
}
