package demo2.ch5.config;

import demo2.ch5.aop.Caculator;
import demo2.ch5.aop.LogAspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志运行 通知 aop
 *
 * @author HASEE
 */
@Configuration
@ComponentScan("demo2.ch5")
@EnableAspectJAutoProxy
public class MainConfig2 {
    @Bean
    public Caculator caculator() {
        return new Caculator();
    }

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
