package com.dao.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:54
 */
@Slf4j
public class MultiMybatisSessionFactory {
    private static SqlSessionTemplate sqlSessionTemplate;

    private static Object LOCK = new Object();

    static SqlSessionTemplate sqlSessionTemplate() {
        synchronized (LOCK) {
            if (sqlSessionTemplate == null) {
                sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
            }
            return sqlSessionTemplate;
        }
    }

    private static SqlSessionFactory sqlSessionFactory() {
        // TODO

        return null;
    }
}
