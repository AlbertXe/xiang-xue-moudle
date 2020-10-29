package com.service;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-10-29 10:56
 */
public class HelloService {
    private String msg;

    public HelloService() {
    }

    public String sayHello() {
        System.out.println("hello," + msg);
        return msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
