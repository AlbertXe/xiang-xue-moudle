package com.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 86150
 * OnewayProducer
 * 2020/12/5 17:12
 */
public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("oneway");
        producer.setNamesrvAddr(RocketmqConstants.host);
        producer.start();
        for (int i = 0; i < 10; i++) {
            String body = "hello MQ" + i;
            Message message = new Message(RocketmqConstants.topicTest, "tagA", body.getBytes());
            // 1 5 10 30 s
            // 1-10 20 30 m
            // 1 2 h
            message.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(message);
            System.out.println(new String(message.getBody()));
            System.out.println("id:" + sendResult.getMsgId());
        }
        producer.shutdown();
    }
}
