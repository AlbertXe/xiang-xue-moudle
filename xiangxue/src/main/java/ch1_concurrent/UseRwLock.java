package ch1_concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 86150
 * UseRwLock
 * 2020/2/18 21:36
 */
public class UseRwLock {
    private int age;

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readlock = readWriteLock.readLock();
    private static Lock writelock = readWriteLock.readLock();

    public int get() {
        readlock.lock();
        age++;
        readlock.unlock();
        return age;
    }

    public void set() {
        writelock.lock();
        age++;
        writelock.unlock();
    }

}
