package ch1_concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 86150
 * UseFuture
 * 2020/2/18 18:58
 */
public class UseFuture {

    private static class A implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("开始线程");
            int count = 0;
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().isInterrupted());
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("已经取消");
                    return null;
                }
                count += i;
            }
            return count;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new A());
        new Thread(futureTask).start();
        Thread.sleep(1);
        futureTask.cancel(true);

    }

}
