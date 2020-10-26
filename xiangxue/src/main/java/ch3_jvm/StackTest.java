package ch3_jvm;

/**
 * javap -v A.class > a.txt
 * 86150
 * StackTest
 * 2020/2/22 11:50
 */
public class StackTest {
    static String ss = "hello";
    final String fs = "java";
    int count = 0;

    public void king(int m) {
        System.out.println(ss);
        System.out.println(fs);
        count = count + 1;
        m = m - 100;
        System.out.println(count);
    }

    public static void main(String[] args) {
        StackTest stackTest = new StackTest();
        stackTest.king(1000);
    }
}
