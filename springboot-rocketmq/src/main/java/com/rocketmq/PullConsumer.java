package com.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 拉模式
 * 86150
 * PullConsumer
 * 2020/12/5 21:15
 */
public class PullConsumer {
    private static final Map<MessageQueue, Long> offset_table = new HashMap<>();

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pullconsumer");
        consumer.setNamesrvAddr(RocketmqConstants.host);
        System.out.println("ms" + consumer.getBrokerSuspendMaxTimeMillis());
        consumer.start();
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(RocketmqConstants.topicTest);
        for (MessageQueue messageQueue : messageQueues) {
            System.out.println("queryId:" + messageQueue.getQueueId());
            // 是否从本地获取 拉取序号
            long offset = consumer.fetchConsumeOffset(messageQueue, true);
            while (true) {
                PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        for (MessageExt ext : pullResult.getMsgFoundList()) {
                            System.out.printf("%s%n", new String(ext.getBody()));
                        }
                        break;
                    case NO_NEW_MSG:
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }

        }


    }

    private static void putMessageQueueOffset(MessageQueue messageQueue, long nextBeginOffset) {
        offset_table.put(messageQueue, nextBeginOffset);
    }

    private static long getMessageQueueOffset(MessageQueue messageQueue) {
        Long aLong = offset_table.get(messageQueue);
        if (aLong != null) {
            return aLong;
        }
        return 0;
    }
}
