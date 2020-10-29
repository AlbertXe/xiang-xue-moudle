package demo2.ch3.config;

import demo2.ch1.Person;
import demo2.ch3.bean.Bike;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("demo2.ch3")
public class MainConfig {
    @Bean
    public Person person() {
        return new Person("james", 20);
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
//	@Scope("prototype")
    public Bike bike() {
        return new Bike();
    }
}
