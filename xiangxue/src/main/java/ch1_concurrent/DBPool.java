package ch1_concurrent;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接处模拟
 * 86150
 * DBPool
 * 2020/2/17 17:26
 */
public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    public DBPool(int init) {
        for (int i = 0; i < init; i++) {
            Connection connection = null;
            pool.addLast(connection);
        }
    }

    public void release(Connection connection) {
        synchronized (pool) {
            pool.addLast(connection);
            pool.notifyAll();//通知其他线程拿链接
        }
    }

    public Connection get(long mils) throws InterruptedException {
        synchronized (pool) {
            long future = System.currentTimeMillis() + mils;
            long remaining = mils;
            while (pool.isEmpty() && remaining > 0) {
                pool.wait(future);
                remaining = future - System.currentTimeMillis();
                if (!pool.isEmpty()) {
                    return pool.getFirst();
                }
            }
            return null;
        }
    }
}
