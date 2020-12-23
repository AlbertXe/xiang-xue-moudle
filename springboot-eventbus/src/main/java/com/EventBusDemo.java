package com;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * 1. 注册事件(添加订阅者)
 *
 * 2. 发起事件通知,依次通知这些订阅者
 *
 * 监听继承  A 继承B的监听
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
    @Subscribe
    public void sendByCompute2r(DeadEvent msg) {
        System.out.println("Dead发送:" + msg);
    }



    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventBusDemo());
        String hello = "hello";
        Integer hello2 = 123;
        eventBus.post(hello);
        eventBus.post(hello2);
        // 发送死信 也就是没有subscribe
        eventBus.post(new Object());
        eventBus.unregister(hello);
        eventBus.unregister(hello2);

    }
}
