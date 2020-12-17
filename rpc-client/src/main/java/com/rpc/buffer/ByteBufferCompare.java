package com.rpc.buffer;

import java.nio.ByteBuffer;

/**
 * 直接内存分配时间 比堆内存耗时
 *
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 23:07
 */
public class ByteBufferCompare {

    public static void main(String[] args) {
        int time = 1000000;

        long s = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        }
        long ms = System.currentTimeMillis() - s;
        System.out.println("time:" + ms);


        long s1 = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        }
        long ms1 = System.currentTimeMillis() - s1;
        System.out.println("time:" + ms1);

    }
}
