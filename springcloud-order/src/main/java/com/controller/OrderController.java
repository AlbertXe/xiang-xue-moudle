package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * OrderController
 * 2020/10/26 23:25
 */
@RestController
public class OrderController {
    @RequestMapping("/order")
    public String order() {
        String s = " this is order";
        System.out.println(s);
        return s;
    }
}
