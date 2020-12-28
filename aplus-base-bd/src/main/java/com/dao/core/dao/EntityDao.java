package com.dao.core.dao;

import java.io.Serializable;
import java.util.List;

public interface EntityDao<T extends Entity> extends Serializable {
    int insert(T t);

    int insert(List<T> tList);

    int insert(String mapperId, Object param);

    int insertBatch(String mapperId, List<T> tList);

    // TODO

}
