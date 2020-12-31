package com.dao.plugins.mybatis.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 11:15
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class MybatisLogInterceptor extends AbstractInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];

        BoundSql boundSql = ms.getSqlSource().getBoundSql(param);
        if (showsql) {
            String sql = boundSql.getSql();
            StringBuilder sb = new StringBuilder();
            sb.append("sql = ").append(sql).append(";");
            sb.append("mapperId = ").append(ms.getId()).append(";");
            sb.append("mapperFile = ").append(ms.getResource()).append(";");
            sb.append("parameters = ").append(JSONUtils.toJSONString(param)).append(";");
            sb.append("parameter class = ").append(ms.getId()).append(";");
            sb.append("parameter type = ").append(null==param?null:param.getClass().getName()).append(";");

            log.info(sb.toString());
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
