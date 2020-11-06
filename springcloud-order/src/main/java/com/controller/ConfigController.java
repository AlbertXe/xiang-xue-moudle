package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 86150
 * ConfigController
 * 2020/11/6 19:16
 */
@RestController
public class ConfigController {
    @Autowired
    Environment environment;

    @RequestMapping("/qryContent")
    public String qryContent(HttpServletRequest request) {
        String name = environment.getProperty("name");
        String serverPort = environment.getProperty("server.port");
        System.out.println("name=" + name);
        System.out.println("server.port=" + serverPort);

        return name;
    }
}
