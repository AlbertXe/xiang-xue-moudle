package com.dao.venus.event;

import com.dao.venus.DefaultDataSource;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-01-04 09:27
 */
@Getter
@Setter
public class DefaultDatasourceEvent {
    private String namespace;
    private DefaultDataSource defaultDataSource;

    public DefaultDatasourceEvent(String namespace, DefaultDataSource defaultDataSource) {
        this.namespace = namespace;
        this.defaultDataSource = defaultDataSource;
    }
}
