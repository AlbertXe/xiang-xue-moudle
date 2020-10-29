package demo2.ch2.config;

import demo2.ch2.bean.Pig;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class JamesImportBeanDefinitionRigister implements ImportBeanDefinitionRegistrar {
    /**
     * 0：当前类的注解信息
     * 1：BeanDefinition注册类
     * 把所有要添加到容器中的bean加入
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean bean1 = registry.containsBeanDefinition("demo2.ch2.bean.Dog");
        boolean bean2 = registry.containsBeanDefinition("demo2.ch2.bean.Fish");
        if (bean1 && bean2) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Pig.class);//往容器中丢。
            registry.registerBeanDefinition("pig", beanDefinition);
        }
    }

}
