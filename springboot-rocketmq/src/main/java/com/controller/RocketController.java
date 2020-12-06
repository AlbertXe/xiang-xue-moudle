package com.controller;

import com.rocketmq.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * RocketController
 * 2020/12/6 22:07
 */
@RestController
@RequestMapping("/rocket")
public class RocketController {
    @Autowired
    MQProducer mqProducer;

    @RequestMapping("/send")
    public String send(@RequestParam(value = "msg") String msg) {
        mqProducer.send(msg, "springbootTest", "tagA", "");
        return "success";
    }
}
