package com.rocketmq.trans;

import com.rocketmq.RocketmqConstants;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 86150
 * TransactionProducer
 * 2020/12/6 17:16
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException {
        TransactionListener listener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("ransaction-producer");
        producer.setNamesrvAddr(RocketmqConstants.host);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("trans");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        producer.setTransactionListener(listener);
        producer.start();

        String[] tags = {"tagA", "tagB", "tagC", "tagD"};

        for (int i = 0; i < 10; i++) {
            String body = "hello mq" + i;
            Message message = new Message(RocketmqConstants.trans, "tagA", "KEY" + i, body.getBytes());
            SendResult sendResult = null;
            try {
                sendResult = producer.sendMessageInTransaction(message, null);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
            System.out.printf("%s==%s==%s==%s %n", sendResult.getSendStatus(), sendResult.getMessageQueue(), sendResult.getMessageQueue().getQueueId(), body);
        }
//        producer.shutdown();
    }
}
