package com.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-19 17:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ListenerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @Test
    public void test() {
        MailEvent event = new MailEvent("object", "5568@qq.com", "content");
        applicationContext.publishEvent(event);
    }
}
