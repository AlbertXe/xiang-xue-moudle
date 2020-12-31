package com.dao.plugins.mybatis.interceptor;

import com.google.common.base.Joiner;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 13:59
 */
@Intercepts({@Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Getter
@Setter
public class PageInterceptor extends AbstractInterceptor {
    private int mapper_statement_index=0;
    private int param_index=1;
    private int rowbounds_index=2;

    private static ExecutorService pool;
    private String dialectClass;
    private boolean asyncTotalCount = false;



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object param = args[1];
        RowBounds rowBounds = (RowBounds) args[2];

        if (true) {
            return invocation.proceed();
        }
        //TODO

        return null;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.dialectClass = properties.getProperty("dialect_class");
        this.asyncTotalCount = Boolean.parseBoolean(properties.getProperty("async_total_count"));
        setPoolSize(Integer.parseInt(properties.getProperty("pool_size")));
    }

    public void setPoolSize(int poolSize) {
        if (poolSize > 0) {
            pool = new ScheduledThreadPoolExecutor(poolSize, new BasicThreadFactory.Builder().namingPattern("page-interceptor-pool-%d").build());
        }else {
            pool = Executors.newCachedThreadPool();
        }
    }

    public static void closePool() {
        if (pool!=null) pool.shutdown();
    }

    private MappedStatement copySql(MappedStatement ms, BoundSql boudSql, String sql, List<ParameterMapping> parameterMappings, Object paramObj) {
        BoundSql newBoundSql = copyBoundSql(ms, boudSql, sql, parameterMappings, paramObj);
        return copyMappedStatement(ms,t->{
            return (BoundSql) t;
        });

    }


    private BoundSql copyBoundSql(MappedStatement ms, BoundSql boundSql, String sql, List<ParameterMapping> parameterMappings, Object paramterObj) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMappings, paramterObj);
        for (ParameterMapping mapping : newBoundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private MappedStatement copyMappedStatement(MappedStatement ms, SqlSource sqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ArrayUtils.isNotEmpty(ms.getKeyProperties())) {
            String join = Joiner.on(",").join(ms.getKeyProperties());
            builder.keyProperty(join);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
}
