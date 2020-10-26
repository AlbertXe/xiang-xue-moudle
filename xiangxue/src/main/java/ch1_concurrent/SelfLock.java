package ch1_concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 86150
 * SelfLock
 * 2020/2/18 23:15
 */
public class SelfLock {
    /**
     * 自动义同步器
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                //排他锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 判断是否再用
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

}
