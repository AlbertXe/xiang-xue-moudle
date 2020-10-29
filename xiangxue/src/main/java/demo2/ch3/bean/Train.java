package demo2.ch3.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Train implements InitializingBean, DisposableBean {

    public Train() {
        System.out.println("train constructor");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy tarin");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init train afterProperties");
    }

}
