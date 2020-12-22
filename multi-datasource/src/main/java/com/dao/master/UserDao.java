package com.dao.master;

import com.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-22 14:03
 */
@Component("masterUserDao")
public interface UserDao {
    @Select("select * from user")
    public List<User> getAll();
}
