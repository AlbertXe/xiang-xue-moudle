package demo2.ch2.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WinCondition implements Condition {

    /**
     *
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();//从IIOC
        Environment environment = context.getEnvironment();
        String name = environment.getProperty("os.name");
        System.out.println(name);
        if (name.contains("Windows"))
            return true;
        return false;
    }

}
