package demo2.ch4.config;

import demo2.ch4.bean.Bird;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("demo2.ch4")
@PropertySource(value = "classpath:/demo2/ch4/test.properties")
public class MainConfig {
    @Bean
    public Bird bird() {
        return new Bird();
    }
}
