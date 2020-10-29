package demo2.ch1.config;

import demo2.ch1.Person;
import demo2.ch1.controller.OrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "demo2.ch1", includeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = {Ch2.class}),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {OrderController.class}),
        @Filter(type = FilterType.CUSTOM, classes = {JamesTypeFilter.class}) // 自定义

}, useDefaultFilters = true) // 如果使用includeFilters  useDefaultFilters=false;
public class MainConfig2 {
    @Bean // 默认方法名就是bean name;
    public Person person() {
        return new Person("james", 20);
    }
}
