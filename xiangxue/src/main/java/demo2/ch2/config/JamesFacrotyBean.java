package demo2.ch2.config;

import demo2.ch2.bean.Monkey;
import org.springframework.beans.factory.FactoryBean;

public class JamesFacrotyBean implements FactoryBean<Monkey> {

    @Override
    public Monkey getObject() throws Exception {
        return new Monkey();
    }

    @Override
    public Class<?> getObjectType() {
        return Monkey.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
