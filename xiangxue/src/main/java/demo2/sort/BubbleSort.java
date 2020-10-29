package demo2.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 */
public class BubbleSort {

    /**
     * 冒泡
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {//表示 趟数
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
        Arrays.stream(arr).forEach(System.out::println);
    }

    @Test
    public void test1() {
        bubbleSort(new int[]{4, 5, 7, 8, 1, 3});
    }


}
