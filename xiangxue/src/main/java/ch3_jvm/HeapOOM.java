package ch3_jvm;

/**
 * -Xms20m -Xmx20m
 * 86150
 * HeapOOM
 * 2020/2/23 19:44
 */
public class HeapOOM {
    public static void main(String[] args) {
        String[] ss = new String[10000000];
    }
}
