package com.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.CountDownLatch;

/**
 * 86150
 * SyncProducer
 * 2020/12/5 18:25
 */
public class AsyncProducer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("async");
        producer.setNamesrvAddr(RocketmqConstants.host);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(1);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            String body = "hello MQ" + i;
            Message message = new Message(RocketmqConstants.topicTest, "tagC", body.getBytes());
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(new String(message.getBody()));
                    System.out.println(sendResult.getMsgId());
                    latch.countDown();
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                }
            });

        }
        latch.await();
        // 消息发送完毕 才能关闭
        producer.shutdown();
    }
}
