package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 86150
 * ConsumerApp
 * 2020/10/27 19:55
 */
@SpringBootApplication
@EnableEurekaClient
//开启熔断
@EnableCircuitBreaker
public class ConsumerApp {
    @Bean
        // 负载均衡  单机时 服务地址上的服务名不能时localhost
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
