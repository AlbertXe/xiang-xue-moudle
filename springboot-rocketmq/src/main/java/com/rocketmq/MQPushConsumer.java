package com.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 86150
 * MQPushConsumer
 * 2020/12/6 22:27
 */
@Controller
@Slf4j
public class MQPushConsumer implements MessageListenerConcurrently {
    @Value("${rocketmq.namesrvaddr}")
    private String namesrvaddr;

    private DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("springbootTest");

    @PostConstruct
    public void start() {
        log.info("init consumer");
        consumer.setNamesrvAddr(namesrvaddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            consumer.subscribe("springbootTest", "*");
            consumer.registerMessageListener(this);
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }


    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        int index = 0;
        for (MessageExt msg : list) {
            String body = new String(msg.getBody());
            log.info("收到消息:{}", body);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @PreDestroy
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            log.info("shutdown consumer");
        }
    }
}
