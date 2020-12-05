package com.rocketmq.order;

import com.rocketmq.RocketmqConstants;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * 部分有序
 * 86150
 * ProducerAllOrder
 * 2020/12/6 1:17
 */
public class ProducerPartOrder {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("partOrder");
        producer.setNamesrvAddr(RocketmqConstants.host);
        producer.start();
        String[] tags = {"tagA", "tagB", "tagC", "tagD"};
        for (int i = 0; i < 12; i++) {
            String body = "hello mq" + i;
            int index = i % tags.length;
            Message message = new Message(RocketmqConstants.partOrder, tags[index], "KEY" + i, body.getBytes());
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    return list.get(index);
                }

            }, i);
            System.out.printf("%s==%s==%s==%s %n", sendResult.getSendStatus(), sendResult.getMessageQueue(), sendResult.getMessageQueue().getQueueId(), body);
        }
        producer.shutdown();
    }
}
