package com.dao.plugins.mybatis.interceptor;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 14:00
 */
@Getter
@Setter
public abstract class AbstractInterceptor implements Interceptor, Serializable {
    protected boolean showsql = false;


}
