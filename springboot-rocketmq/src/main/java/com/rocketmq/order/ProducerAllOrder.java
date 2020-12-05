package com.rocketmq.order;

import com.rocketmq.RocketmqConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 86150
 * ProducerAllOrder
 * 2020/12/6 1:17
 */
public class ProducerAllOrder {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("allOrder");
        producer.setNamesrvAddr(RocketmqConstants.host);
        producer.start();
        for (int i = 0; i < 5; i++) {
            String body = "hello mq" + i;
            Message message = new Message(RocketmqConstants.allOrder, "tagA", "KEY" + i, body.getBytes());
            SendResult sendResult = producer.send(message);
            System.out.printf("%s==%s==%s==%s %n", sendResult.getSendStatus(), sendResult.getMessageQueue(), sendResult.getMessageQueue().getQueueId(), body);
        }
        producer.shutdown();
    }
}
