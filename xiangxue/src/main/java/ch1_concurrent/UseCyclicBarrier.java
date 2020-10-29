package ch1_concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 86150
 * UseCycliBarrier
 * 2020/2/18 1:30
 */
public class UseCyclicBarrier {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new A());

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new B().start();
        }
    }

    /**
     * 做汇总
     */
    private static class A implements Runnable {
        @Override
        public void run() {
            //会在线程都到达了 才开始
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("总汇总信息");
        }
    }

    private static class B extends Thread {
        @Override
        public void run() {
            System.out.println("子线程开始等待");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("子线程等待结束");

        }
    }

}
