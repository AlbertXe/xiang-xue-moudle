package com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 86150
 * UserServiceImpl
 * 2020/10/9 10:03
 */
@Service
public class UserServiceImpl implements UserService {

    public void saveUser(String user) {
        System.out.println("保存=={}成功"+user);
    }
}
