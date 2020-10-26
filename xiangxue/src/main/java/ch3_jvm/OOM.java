package ch3_jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * 86150
 * OOM
 * -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * 2020/2/22 15:25
 */
public class OOM {
    public static void main(String[] args) {
        List<Object> ls = new LinkedList<>();
        int i = 0;
        while (true) {
            i++;
            if (i / 10000 == 0) System.out.println(i);
            ls.add(new Object());
        }
    }
}
