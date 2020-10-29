package demo2.ch1;

import demo2.ch1.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Cap2Test {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);

        String[] names = context.getBeanDefinitionNames();//调用bean定义
        for (String name : names) {
            System.out.println(name);
        }

    }

    @Test
    public void test2() {
        BigDecimal b = new BigDecimal("0000011.0001");
        DecimalFormat format = new DecimalFormat("0.####");
        String s = format.format(b);
        System.out.println(s);
    }
}
