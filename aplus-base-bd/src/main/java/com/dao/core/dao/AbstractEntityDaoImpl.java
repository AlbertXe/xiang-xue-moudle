package com.dao.core.dao;

import com.dao.core.DatasourceConfigProperties;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @description: 所有dao的超类
 * @author: AlbertXe
 * @create: 2020-12-28 16:29
 */
public abstract class AbstractEntityDaoImpl<T extends Entity> extends AbstractBaseDaoImpl<T> implements EntityDao<T>  {
    private static Map<String, Boolean> cacheMap = new ConcurrentHashMap<>();
    @Value("${aplus.db.batch-size:100}")
    private int batchSize;
    @Value("${aplus.db.update-batch-size:100}")
    private int updateBatchSize;

    @Autowired
    DatasourceConfigProperties datasourceConfigProperties;

    @Override
    public int insert(T t) {
        this.insert("insert", t);
        return 0;
    }

    @Override
    public int insert(List<T> tList) {
        return insertBatch("insertBatch", tList);
    }

    @Override
    public int insert(String mapperId, Object param) {
        getSqlSession().insert(getMapperId(mapperId), param);
        if (param instanceof List) {
            return ((List) param).size();
        }
        getSqlSession().flushStatements();
        return 1;
    }

    @Override
    public int insertBatch(String mapperId, List<T> tList) {
        SqlSession sqlSession = getSqlSession();
        int count = 0;

        if (datasourceConfigProperties.getDialect().toUpperCase().contains("MYSQL")) {
            List<T> tempList = Lists.newArrayListWithExpectedSize(100);
            for (T t : tempList) {
                tempList.add(t);
                if (tempList.size() == batchSize) {
                    count += this.insert(mapperId, tempList);
                    getSqlSession().flushStatements();
                    tempList.clear();
                }
            }
            if (!tempList.isEmpty()){
                count += this.insert(mapperId, tempList);
                getSqlSession().flushStatements();
                tempList.clear();
            }

        }else {
            count = insert(tList);
        }

        return count;
    }

    /**
     * 通用sql
     * @param tList
     * @return
     */
    public int insertGeneral(List<T> tList) {
        int count = 0;
        for (T t : tList) {
            count += insert(t);
            if (count % batchSize == 0) {
                getSqlSession().flushStatements();
            }
        }
        if (count % batchSize != 0) {
            getSqlSession().flushStatements();
        }
        return count;
    }

    // TODO
}
