package com.dao.core.transaction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 13:50
 */
@Configuration
@Getter
@Setter
public class TransactionManagerConfiguration {
    private static final CatDatabaseTransactionWatcher WATCHER = new CatDatabaseTransactionWatcher();

    @Value("${aplus.cat.transaction.monitor.enabled:true}")
    private boolean enableTransactionManager;
    @Autowired
    private DataSource dataSource;
    @Value("${aplus.base.transaction.timeOut:-1}")
    private int timeOut;


    @Bean("transactionManager")
    @Primary
    @ConditionalOnProperty(prefix = "aplus.cat.transaction.monitor",name = "enabled",havingValue = "true",matchIfMissing = true)
    public DataSourceTransactionManager aplusMonitoringTransactionManager() {
        DataSourceTransactionManager transactionManager = getTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setDefaultTimeout(timeOut);
        return transactionManager;
    }

    private DataSourceTransactionManager getTransactionManager() {
        if (enableTransactionManager) {
            // 事务管理器
            return new DataSourceTransactionManager(){
                @Override
                protected void prepareForCommit(DefaultTransactionStatus status) {
                    if (TransactionSynchronizationManager.isSynchronizationActive()) {
                        TransactionSynchronizationManager.registerSynchronization(WATCHER);
                    }
                }

                @Override
                protected void doBegin(Object transaction, TransactionDefinition definition) {
                    super.doBegin(transaction, definition);
                    // cat to do
                }

                @Override
                protected void doRollback(DefaultTransactionStatus status) {
                    super.doRollback(status);
                    // cat to do
                }
            };

        }else {
            return new DataSourceTransactionManager();
        }
    }


}
