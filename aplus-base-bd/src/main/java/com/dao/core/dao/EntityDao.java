package com.dao.core.dao;

import java.io.Serializable;
import java.util.List;

public interface EntityDao<T extends Entity> extends Serializable {
    int insert(T t);

    int insert(List<T> tList);

    int insert(String mapperId, Object param);

    int insertBatch(String mapperId, List<T> tList);

    int insertNotNull(T t);
    int insertNotNull(List<T> tList);

    int update(T t);
    int update(List<T> t);
    int update(String mapperId, Object param);
    int updateAll(String mapperId, List<T> tList);
    int updateNotNull(T t);
    int updateNotNull(List<T> tList);




    // TODO

}
