package ch1_concurrent;

import java.util.concurrent.RecursiveTask;

/**
 * 86150
 * SumArray
 * 2020/2/17 19:18
 */
public class SumArray {
    private static class SumTask extends RecursiveTask<Integer> {
        private int from;
        private int to;
        private int[] src;
        private final static int length = 10;

        public SumTask(int from, int to, int[] src) {
            this.from = from;
            this.to = to;
            this.src = src;
        }

        protected Integer compute() {
            if (to - from <= length) {
                int count = 0;
                for (int i = from; i <= to; i++) {
                    count += src[i];
                }
                return count;
            } else {
                // from mid to
                int mid = (from + to) / 2;
                SumTask left = new SumTask(from, mid, src);
                SumTask right = new SumTask(mid + 1, to, src);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }
    }
}
