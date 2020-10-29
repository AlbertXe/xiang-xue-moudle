package demo2.ch2.config;

import demo2.ch1.Person;
import demo2.ch2.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {Dog.class, JamesImportSelector.class, JamesImportBeanDefinitionRigister.class})
public class MainConfig2 {
    /**
     * 给容器注册组件
     * 1.@Bean   这个太简单
     * 2.@ComponentScan   包扫描+注解  针对自己写的类
     * 3.@Import: 快速给容器导入组件   bean id 为全类名
     * 4.@ImportSelector
     * 5.implements ImportBeanDefinitionRegistrar
     *
     * @return
     */

    @Bean
    public Person person() {
        return new Person("james", 20);
    }

    @Bean
    public JamesFacrotyBean monkey() {
        return new JamesFacrotyBean();
    }
}
