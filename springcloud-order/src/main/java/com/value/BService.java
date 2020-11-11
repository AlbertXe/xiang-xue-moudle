package com.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 20:17
 */
@Service
@Configurable(preConstruction = true)
@EnableSpringConfigured
public class BService {
    @Autowired
    AService aService;

    public void get() {
        System.out.println("BService");
        aService.get();
    }
}
