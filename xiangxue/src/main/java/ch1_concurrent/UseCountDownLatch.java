package ch1_concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 86150
 * UseCountDownLatch
 * 2020/2/17 20:06
 */
public class UseCountDownLatch {
    private static CountDownLatch latch = new CountDownLatch(6);

    private static class InitThread extends Thread {
        @Override
        public void run() {
            latch.countDown();
            System.out.println("扣减成功");
        }
    }

    private static class JobThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "等待");
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            latch.countDown();
            latch.countDown();
        }).start();
        for (int i = 0; i < 3; i++) {
            new InitThread().start();
        }
        latch.countDown();
        System.out.println("全部执行成功");
    }


}
