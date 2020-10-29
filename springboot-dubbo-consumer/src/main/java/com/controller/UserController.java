package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.service.HelloService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 86150
 * UserController
 * 2020/10/9 10:41
 */
@RestController
public class UserController {
    @Reference
    UserService userService;
    @Autowired
    HelloService helloService;

    @RequestMapping
    public String saveUser(String name) {
        userService.saveUser(name);
        return "保存用户" + name + "success";
    }

    @RequestMapping("/hello")
    public String hello() {
        helloService.sayHello();
        return "sucess";
    }

}
