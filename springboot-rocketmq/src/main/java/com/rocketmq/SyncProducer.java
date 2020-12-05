package com.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 86150
 * SyncProducer
 * 2020/12/5 18:25
 */
public class SyncProducer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("sync");
        producer.setNamesrvAddr(RocketmqConstants.host);
        producer.setRetryTimesWhenSendFailed(1);
        producer.start();

        for (int i = 0; i < 10; i++) {
            String body = "hello MQ" + i;
            Message message = new Message(RocketmqConstants.topicTest, "tagB", body.getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(new String(message.getBody()));
            System.out.println(sendResult.getMsgId());
        }
        producer.shutdown();
    }
}
