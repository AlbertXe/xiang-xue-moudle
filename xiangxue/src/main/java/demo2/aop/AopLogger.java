package demo2.aop;

public class AopLogger {
    public void logBefore() {
        System.out.println("log before");
    }

    public void logAfter() {
        System.out.println("log after");
    }
}
