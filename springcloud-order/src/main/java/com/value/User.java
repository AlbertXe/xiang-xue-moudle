package com.value;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-11 21:29
 */
@Getter
@Setter
@Component
@MyScope
public class User {
    private String name;

    public User() {
        System.out.println("创建user:" + this);
        this.name = UUID.randomUUID().toString();
    }

}
