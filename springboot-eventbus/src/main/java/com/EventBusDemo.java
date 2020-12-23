package com;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * 1. 注册事件(添加订阅者)
 *
 * 2. 发起事件通知,依次通知这些订阅者
 * 86150
 * EventBusDemo
 * 2020/12/23 22:52
 */
public class EventBusDemo {
    @Subscribe
    public void sendByEmail(String msg) {
        System.out.println("邮件发送:" + msg);
    }
    @Subscribe
    public void sendByPhone(String msg) {
        System.out.println("手机发送:" + msg);
    }
    @Subscribe
    public void sendByComputer(Integer msg) {
        System.out.println("手机发送:" + msg);
    }


    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventBusDemo());
        String hello = "hello";
        Integer hello2 = 123;
        eventBus.post(hello);
        eventBus.post(hello2);
        eventBus.unregister(hello);
        eventBus.unregister(hello2);

    }
}
