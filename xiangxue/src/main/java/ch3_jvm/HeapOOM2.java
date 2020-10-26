package ch3_jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xms20m -Xmx20m
 * 86150
 * HeapOOM2
 * 2020/2/23 19:46
 */
public class HeapOOM2 {
    public static void main(String[] args) {
        List list = new LinkedList();
        int i = 0;
        while (true) {
            if (++i % 10000 == 0) {
                System.out.println(i);
            }
            list.add(new Object());
        }

    }
}
