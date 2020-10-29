package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-10-29 10:52
 */
@ConfigurationProperties(prefix = "hello")
public class HelloConfigProperties {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
