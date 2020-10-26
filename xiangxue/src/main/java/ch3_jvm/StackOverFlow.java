package ch3_jvm;

/**
 * 栈溢出
 * 86150
 * StackOverFlow
 * 2020/2/23 19:41
 */
public class StackOverFlow {
    public static void main(String[] args) {
        king();
    }

    public static void king() {
        king();
    }
}
