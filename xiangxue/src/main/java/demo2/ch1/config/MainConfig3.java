package demo2.ch1.config;

import demo2.ch1.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig3 {
    @Bean // 默认方法名就是bean name;
    @Scope("prototype")
    public Person person() {
        return new Person("james", 20);
    }
}
