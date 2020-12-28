package com.dao.core.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支持多数据源  重写getSqlSession方法
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:38
 */
public class TsqSqlSessionSupport extends SqlSessionDaoSupport {

    /**
     * 1.0.0 以上mybatis需要手工注入 不会自动注入
     * @param sqlSessionTemplate
     */
    @Autowired(required = false)
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Override
    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    @Override
    protected void checkDaoConfig() {
        // 不校验
    }
}
