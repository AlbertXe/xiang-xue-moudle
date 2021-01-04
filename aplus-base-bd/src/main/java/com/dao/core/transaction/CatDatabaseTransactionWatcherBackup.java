package com.dao.core.transaction;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 13:59
 */
public class CatDatabaseTransactionWatcherBackup{
    private static final String CAT_TRANS_TYPE = "SQL";

    private final ThreadLocal<Object> threadLocal = ThreadLocal.withInitial(() -> new Object());

    public CatDatabaseTransactionWatcherBackup() {
        TransactionStatus.init();
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(ApplicationEvent event) {
        threadLocal.get();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletion(ApplicationEvent event) {
        completeCatTransaction();

    }

    private void completeCatTransaction() {
        Object o = threadLocal.get();
        try {
            //TODO
        }finally {
            threadLocal.remove();
        }

    }

    @Getter
    private enum TransactionStatus{
        STATUS_COMMITED(0),
        STATUS_ROLLED_BACK(1),
        STATUS_UNKNOWN(1);

        private int statusCode;
        private static final Map<Integer, TransactionStatus> cache = new HashMap<>();

        TransactionStatus(int statusCode) {
            this.statusCode = statusCode;
        }

        public static TransactionStatus getTransactionStatus(int statusCode) {
            return cache.get(statusCode);
        }

        public synchronized static void init() {
            for (TransactionStatus value : TransactionStatus.values()) {
                cache.put(value.getStatusCode(), value);
            }
        }

        public static void clear() {
            cache.clear();
        }
    }
}
