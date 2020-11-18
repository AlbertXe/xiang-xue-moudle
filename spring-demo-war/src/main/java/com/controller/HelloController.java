package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * HelloController
 * 2020/11/18 21:38
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "欢迎来springboot web!";
    }
}
