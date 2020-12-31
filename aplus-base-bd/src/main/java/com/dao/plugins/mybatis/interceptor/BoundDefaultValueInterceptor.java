package com.dao.plugins.mybatis.interceptor;

import com.dao.core.po.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 11:15
 */
@Getter
@Setter
@Intercepts(@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}))
public class BoundDefaultValueInterceptor extends AbstractInterceptor {
    private boolean enabledDsu;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();

        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];

        // 非insert update 结束
        if (ms.getSqlCommandType() != SqlCommandType.INSERT && ms.getSqlCommandType() != SqlCommandType.UPDATE) {
           return invocation.proceed();
        }
        //当前用户连接名
        String userName = executor.getTransaction().getConnection().getMetaData().getUserName();

        if (param instanceof AbstractEntity) {
            boundValue(ms, userName, (AbstractEntity) param);
            return invocation.proceed();
        }

        if (param instanceof DefaultSqlSession.StrictMap) {
            DefaultSqlSession.StrictMap map = (DefaultSqlSession.StrictMap) param;
            if (map.containsKey("list")) {
                List<?> values = (List<?>) map.get("list");
                for (Object value : values) {
                    boundValue(ms, userName, (AbstractEntity) value);
                }

            }
        } else if (param instanceof Map) {
            //TODO
            Object entity = ((Map) param).get("record");
            if (entity != null && entity instanceof AbstractEntity) {
                boundValue(ms, userName, (AbstractEntity) entity);
            }
        }
        return invocation.proceed();
    }

    private void boundValue(MappedStatement ms, String userName, AbstractEntity entity) {
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            entity.setJpaVersion(0);

            if (entity.isTimeSet()) {
                entity.setCreateTime(LocalDateTime.now());
                entity.setUpdateTime(LocalDateTime.now());
            }
        }else {
            entity.setUpdateTime(LocalDateTime.now());
        }
        if (enabledDsu) {
            entity.setDsuNo("DSU100");
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
