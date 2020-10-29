package demo2.ch2.config;

import demo2.ch1.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {
    @Bean  // 默认方法名就是bean name;
    public Person person() {
        System.out.println("init person");
        return new Person("james", 20);
    }

    @Conditional(WinCondition.class)
    @Bean
    public Person linson() {
        System.out.println("init linson");
        return new Person("linson", 21);
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public Person james() {
        System.out.println("init james");
        return new Person("james", 21);
    }
}
