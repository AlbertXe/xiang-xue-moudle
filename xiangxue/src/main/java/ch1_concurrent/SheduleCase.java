package ch1_concurrent;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 86150
 * SheduleCase
 * 2020/2/20 23:13
 */
public class SheduleCase {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
        schedule.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行一次");
            }
        }, 3, TimeUnit.SECONDS);

        //间隔执行
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时10秒");
            }
        }, 5, 10, TimeUnit.SECONDS);

        schedule.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("延时");
            }
        }, 5, 10, TimeUnit.SECONDS);
    }
}
