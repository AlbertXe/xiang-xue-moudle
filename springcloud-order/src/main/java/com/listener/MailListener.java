package com.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description: 邮件监听器
 * @author: AlbertXe
 * @create: 2020-11-19 17:43
 */
@Component
public class MailListener implements ApplicationListener<MailEvent> {
    @Override
    public void onApplicationEvent(MailEvent event) {
        System.out.println("邮件地址:" + event.getMail());
        System.out.println("邮件内容:" + event.getContent());
    }
}
