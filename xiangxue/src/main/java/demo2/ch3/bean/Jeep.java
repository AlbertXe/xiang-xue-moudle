package demo2.ch3.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Jeep {
    public Jeep() {
        System.out.println("jeep constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("init jeep");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy jeep");
    }
}
