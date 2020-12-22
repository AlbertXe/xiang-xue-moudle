package com.controller;

import com.dao.master.UserDao;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-22 14:02
 */
@RestController
public class UserController {
    @Autowired
    @Qualifier("masterUserDao")
    UserDao userDao;

    @Qualifier("slaveUserDao")
    @Autowired()
    com.dao.slave.UserDao userDao2;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/user")
    public String user() {
        List<User> users = userDao.getAll();
        List<User> users2 = userDao2.getAll();

        ArrayList<User> result = new ArrayList<>(users);
        result.addAll(users2);
        return result.toString();
    }

}
