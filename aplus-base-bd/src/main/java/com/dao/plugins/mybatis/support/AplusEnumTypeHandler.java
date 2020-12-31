package com.dao.plugins.mybatis.support;

import com.dao.core.enums.EnumTypeDefine;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * mybatis枚举类型拓展
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-29 10:21
 */
public class AplusEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;

    public AplusEnumTypeHandler(Class<E> enumCls) {
        this.type = enumCls;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            preparedStatement.setString(i,setEnum(e.name()));
        }else {
            preparedStatement.setObject(i,setEnum(e.name()),jdbcType.TYPE_CODE);
        }
    }



    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        return getEnum(result);
    }



    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return getEnum(resultSet.getString(i));
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return getEnum(callableStatement.getString(i));
    }

    /**
     * 根据枚举名获取要入库的枚举信息
     * @param name
     * @return
     */
    private String setEnum(String name) {
        if (name == null) {
            return null;
        }
        if (!isEnumDefine()) {
            return name;
        }

        E[] es = type.getEnumConstants();
        for (E e : es) {
            if (name.equals(e.name())) {
                try {
                    Method method = type.getMethod("getValue");
                    String s = String.valueOf(method.invoke(e, new Object[]{}));
                    return s;
                } catch (Exception e1) {
                }
            }
        }
        throw new RuntimeException("enum "+name+" not found");

    }

    private E getEnum(String s) {
        if (s == null) return null;

        if (!isEnumDefine()) {
            return Enum.valueOf(type, s);
        }

        E[] es = type.getEnumConstants();
        for (E e : es) {
            Method method = null;
            try {
                method = type.getMethod("getValue");
                String str = String.valueOf(method.invoke(e, new Object[]{}));
                if (s.equals(str)) {
                    return e;
                }
            } catch (Exception e1) {
            }
        }
        throw new RuntimeException("enum "+s+" not found");

    }

    private boolean isEnumDefine() {
        Class<?>[] interfaces = type.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces)) {
            return false;
        }
        List<Class<?>> list = Arrays.asList(interfaces);
        if (!CollectionUtils.contains(list.iterator(), EnumTypeDefine.class)) {
            return false;
        }
        return true;
    }
}
