package ch3_jvm;

/**
 * -Xms20m -Xmx20m -XX:+PrintGCDetails
 * 86150
 * JInfoTest
 * 2020/2/23 22:07
 */
public class JInfoTest {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            byte[] b = null;
            for (int i = 0; i < 10; i++) {
                b = new byte[1024 * 1024];
            }
            Thread.sleep(5000);
        }
    }
}
