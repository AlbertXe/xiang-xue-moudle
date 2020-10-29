package com.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

/**
 * 86150
 * TicketService
 * 2020/10/29 20:19
 */
@Service
public class TicketService {
    @HystrixCommand(fallbackMethod = "qryTicketFallback",
            commandKey = "qryTicket",
            groupKey = "group-one",
//            commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")},
            threadPoolKey = "qryTicketPool",
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "5")})
    public String qryTicket() {
        System.out.println("购票服务");
        return "ticket";
    }

    public String qryTicketFallback() {
        return "fallback";
    }
}
