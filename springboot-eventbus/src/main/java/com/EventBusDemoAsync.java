package com;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executor;

/**
 * 1. 注册事件(添加订阅者)
 *
 * 2. 发起事件通知,依次通知这些订阅者
 * 86150
 * EventBusDemo
 * 2020/12/23 22:52
 */
public class EventBusDemoAsync {
    @Subscribe
    public void sendByEmail(String msg) {
        System.out.println("邮件发送:" + msg);
    }
    @Subscribe
    public void sendByPhone(String msg) {
        System.out.println("手机发送:" + msg);
    }
    @Subscribe
    @AllowConcurrentEvents()
    public void sendByComputer(Integer msg) {
        System.out.println("电脑发送:" + msg);
        System.out.println("电脑发送:" + msg);
    }


    public static void main(String[] args) throws InterruptedException {
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };

        AsyncEventBus eventBus = new AsyncEventBus("async",executor);
        eventBus.register(new EventBusDemoAsync());
        String hello = "async hello";
        Integer hello2 = 123;
        eventBus.post(hello);
        eventBus.post(hello2);
        Thread.sleep(8000);
        eventBus.unregister(hello);
        eventBus.unregister(hello2);
        System.out.println("验证异步发送");

    }
}
