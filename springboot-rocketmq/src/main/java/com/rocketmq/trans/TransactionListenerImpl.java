package com.rocketmq.trans;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 86150
 * TransactionListenerImpl
 * 2020/12/6 17:27
 */
public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 判断事物之行成功或者失败  然后时提交或者回滚
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        int value = count.getAndIncrement();
        if (value % 3 == 0) {
            System.out.println("commit==" + new String(message.getBody()));
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if (value % 3 == 1) {
            System.out.println("unknow==" + new String(message.getBody()));
            return LocalTransactionState.UNKNOW;
        } else {
            System.out.println("rollback==" + new String(message.getBody()));
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 事物回查   查看unknow状态的消息
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("事物回查==" + messageExt.getTransactionId());
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
