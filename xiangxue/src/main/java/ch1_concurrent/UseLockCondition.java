package ch1_concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 86150
 * UseLockCondition
 * 2020/2/18 22:05
 */
public class UseLockCondition {
    private int km;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void changeKm() {
        lock.lock();
        km = 101;
        condition.signal();
        lock.unlock();
    }

    class A extends Thread {
        @Override
        public void run() {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
