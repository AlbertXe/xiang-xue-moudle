package ch9_zk;

import ch1_concurrent.User;

import java.util.List;

/**
 * 86150
 * UserDao
 * 2020/3/24 22:03
 */
public interface UserDao {

    List<User> selectList();
}
