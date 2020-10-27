package com.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * OrderController
 * 2020/10/26 23:25
 */
@RestController
public class OrderController {
    public static boolean db_check = true;

    @RequestMapping("/order")
    public String order() {
        String s = " this is order";
        System.out.println(s);
        return s;
    }

    @RequestMapping("/health/db/{id}")
    public String db(@PathVariable int id) {
        if (id % 3 == 1) {
            db_check = false;
            return "db不可用";
        } else {
            db_check = true;
            return "db is ok";
        }
    }
}
