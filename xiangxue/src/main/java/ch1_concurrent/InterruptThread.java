package ch1_concurrent;

/**
 * 中断线程
 * interrupt 发起中断   a.isInterrupted()判断是否终端 不重置中断标志  Thread.interrupted() 重置中断标志
 */
public class InterruptThread {

    private static class A extends Thread {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
//            while (!this.isInterrupted()) {
            while (!Thread.interrupted()) {
                System.out.println(name + "is running");
                System.out.println("flag is " + isInterrupted());
            }
            System.out.println("final flag is " + isInterrupted());
        }
    }

    private static class B extends Thread {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (!this.isInterrupted()) {
//            while (!Thread.interrupted()){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // *** 该抓住异常会将 flag 从true->false 真正中断需要 interrupt();
                    e.printStackTrace();
                }
                System.out.println(name + "is running");
                System.out.println("flag is " + isInterrupted());

                //sleep wait 这么设置的原因 由程序员做资源释放,中断由程序员掌握
                interrupt();//再次调用才会 真正中断
            }
            System.out.println("final flag is " + isInterrupted());
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        A a = new A();
        B a = new B();
        a.start();

        Thread.sleep(30);
        a.interrupt();
    }

}
