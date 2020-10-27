package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 86150
 * ConsumerService
 * 2020/10/27 20:00
 */
@Service
public class ConsumerService {

    private AtomicInteger a = new AtomicInteger();
    private String order = "springcloud-order";

    @Autowired
    RestTemplate restTemplate;

    public String qryOrder() {
        a.incrementAndGet();
        String http = "http://" + order + "/order";
        String result = restTemplate.getForObject(http, String.class);
        return result;

    }

}
