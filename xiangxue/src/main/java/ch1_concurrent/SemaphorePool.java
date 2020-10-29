package ch1_concurrent;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 86150
 * SemaphorePool
 * 2020/2/18 2:00
 */
public class SemaphorePool {
    private static LinkedList<Connection> pool = new LinkedList<>();
    private static Semaphore useful, useless;

    static {
        for (int i = 0; i < 10; i++) {
            Connection connection = null;
            pool.addLast(connection);
        }
    }

    public SemaphorePool() {
        useful = new Semaphore(10);
        useless = new Semaphore(0);
    }

    public Connection getCon() throws InterruptedException {
        useful.acquire();// 没有资源就等待
        Connection con = null;
        synchronized (pool) {
            con = pool.getFirst();
        }
        useless.release();
        return con;
    }
}
