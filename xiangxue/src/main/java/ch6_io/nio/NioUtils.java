package ch6_io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 86150
 * NioUtils
 * 2020/3/7 13:17
 */
public class NioUtils {
    public static void doWrite(Selector selector, SocketChannel channel, String msg) throws IOException {
        byte[] bytes = msg.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer);  //这个是一次写完

        //将byteBuffer 挂在到channel
//        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE,byteBuffer);
    }
}
