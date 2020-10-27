package com.controller;

import com.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * ConsumerController
 * 2020/10/27 19:58
 */
@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @RequestMapping("/consumer")
    public String consumer() {
        String result = consumerService.qryOrder();

        System.out.println("result:======" + result);
        return result;
    }
}
