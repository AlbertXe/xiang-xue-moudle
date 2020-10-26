package ch1_concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 86150
 * MyThreadPool
 * 2020/2/20 18:07
 */
public class MyThreadPool {

    private static int count = 5;
    private static BlockingQueue<Runnable> tasks;
    private static ThreadA[] threads;

    private int work_number;

    public MyThreadPool(int task_count) {
        this(task_count, count);
    }

    public MyThreadPool(int task_count, int work_number) {
        this.work_number = work_number < 0 ? count : work_number;
        tasks = new ArrayBlockingQueue<>(task_count);

        threads = new ThreadA[work_number];
        for (int i = 0; i < work_number; i++) {
            threads[i] = new ThreadA();
            threads[i].start();
        }
    }

    public void destroy() {
        for (int i = 0; i < work_number; i++) {
            threads[i].stopWork();
            threads[i] = null;
        }
        tasks.clear();
    }

    private static class ThreadA extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            while (!isInterrupted()) {
                try {
                    r = tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (r != null) {
                    System.out.println("开始执行 ");
                    r.run();
                    r = null;
                }
            }

        }

        public void stopWork() {
            interrupt();
        }

        /**
         * 放入任务
         *
         * @param runnable
         */
        public void execute(Runnable runnable) {
            try {
                tasks.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
