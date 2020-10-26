package ch3_jvm;

/**
 * 1 锁的内存语义 解锁：本地内存刷新到主内存
 * 加锁：主内存读取共享变量
 * 86150
 * SysMemory
 * 2020/2/21 23:29
 */
public class SysMemory {
    static boolean flag;
    static int number;

    static class A extends Thread {
        @Override
        public void run() {
            while (!flag) {
                //这个打印将 flag 和 number 刷新到 主内存
                System.out.println("number=" + number);
            }
            System.out.println("number=" + number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new A().start();
        Thread.sleep(1);
        flag = true;
        number = 59;

    }


}
