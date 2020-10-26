package ch2_concurrent;

/**
 * 86150
 * DeadLock
 * 2020/2/21 1:40
 */
public class DeadLock {
    static Object o1 = new Object();
    static Object o2 = new Object();

    static class A extends Thread {
        @Override
        public void run() {
            synchronized (o1) {
                System.out.println("A o1");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("A o2");
                }
            }
        }
    }

    static class B extends Thread {
        @Override
        public void run() {
            synchronized (o2) {
                System.out.println("B o1");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("B o2");
                }
            }
        }
    }

    public static void main(String[] args) {
        new A().start();
        new B().start();

    }
}
