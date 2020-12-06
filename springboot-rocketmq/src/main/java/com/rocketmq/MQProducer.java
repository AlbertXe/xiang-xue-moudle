package com.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 86150
 * MQProducer
 * 2020/12/6 22:10
 */
@Component
@Slf4j
public class MQProducer {
    @Value("${rocketmq.namesrvaddr}")
    private String namesrvaddr;

    private DefaultMQProducer producer = new DefaultMQProducer("springbootTest");

    /**
     * 初始化
     */
    @PostConstruct
    public void start() {
        log.info("producer init");
        producer.setNamesrvAddr(namesrvaddr);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void send(String data, String topic, String tag, String key) {
        Message message = new Message(topic, tag, data.getBytes());
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("成功发送消息：" + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                    throw new RuntimeException(throwable);
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
