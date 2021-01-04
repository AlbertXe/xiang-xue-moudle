package com.dao.core.transaction;

import lombok.Getter;
import org.springframework.transaction.support.TransactionSynchronization;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 13:59
 */
public class CatDatabaseTransactionWatcher implements TransactionSynchronization {
    private static final String CAT_TRANS_TYPE = "SQL";

    private final ThreadLocal<Object> threadLocal = ThreadLocal.withInitial(() -> new Object());

    public CatDatabaseTransactionWatcher() {
        TransactionStatus.init();
    }

    @Override
    public void beforeCommit(boolean readOnly) {
        threadLocal.get();
    }

    @Override
    public void afterCompletion(int status) {
        completeCatTransaction(status);

    }

    private void completeCatTransaction(int status) {
        Object o = threadLocal.get();
        TransactionStatus transactionStatus = TransactionStatus.getTransactionStatus(status);
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
