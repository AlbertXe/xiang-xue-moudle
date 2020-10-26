package ch3_jvm;

import java.nio.ByteBuffer;

/**
 * -Xms40m //初始内存
 * -Xmx256m //最大内存
 * -Xmn16m //最小内存
 * 86150
 * ByteBufferRam
 * 2020/2/22 12:56
 */
public class ByteBufferRam {
    public static void main(String[] args) {
        ByteBuffer.allocateDirect(1024 * 1024 * 100);

    }
}
