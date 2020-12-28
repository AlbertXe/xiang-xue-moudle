package com.dao.core.dao;

import java.io.Serializable;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-28 16:33
 */
public interface Entity extends Serializable {
    Long getId();

    void setId(Long id);

}
