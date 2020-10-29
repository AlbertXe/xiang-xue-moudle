package demo2.ch1;

import demo2.ch1.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Test1 {

    public static void main(String[] args) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        Person person = (Person) context.getBean("abc");
        System.out.println(person);
        String[] names = context.getBeanNamesForType(Person.class);
        System.out.println(Arrays.asList(names));
    }
}
