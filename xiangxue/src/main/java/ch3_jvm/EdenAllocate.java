package ch3_jvm;

/**
 * -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
 * 86150
 * EdenAllocate
 * 2020/2/22 13:52
 */
public class EdenAllocate {
    private static final int sum = 1024 * 1024;

    public static void main(String[] args) {
        byte[] b1, b2, b3;
        b1 = new byte[sum];
        b2 = new byte[sum];
        b3 = new byte[5 * sum];
    }
}
