package com.dao.core.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:35
 */
public abstract class AbstractBaseDaoImpl<T extends Entity> extends TsqSqlSessionSupport implements
        Serializable {

    /**
     * 如果继承EntityDao的都必须实现, 以获取 mapper文件的namespace
     *
     * @return
     */
    protected abstract String getNameSpace();

    protected String getMapperId(String mapperId) {
        if (StringUtils.isBlank(getNameSpace())) return mapperId;
        String nameSpace = getNameSpace();
        if (mapperId.startsWith(nameSpace)) {
            return mapperId;
        }
        return nameSpace + "." + mapperId;
    }

    /**
     * 兼容 ExecutorType.BATCH ExecutorType.SIMPLE
     * @param result
     * @return
     */
    protected int result(int result) {
        SqlSessionTemplate sqlSession = (SqlSessionTemplate) this.getSqlSession();
        if (ExecutorType.BATCH != sqlSession.getExecutorType()) return result;

        // 兼容MYSQL
        if (result>0) return result;

        List<BatchResult> results = this.getSqlSession().flushStatements();
        // TODO ??
        int[] updateCounts = results.get(0).getUpdateCounts();

        return Arrays.stream(updateCounts).sum();

    }


}
