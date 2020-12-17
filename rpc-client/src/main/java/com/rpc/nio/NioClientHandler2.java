package com.rpc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 23:18
 */
public class NioClientHandler2 implements Runnable {
    private String host;
    private int port;
    private volatile boolean started;
    private Selector selector;
    private SocketChannel socketChannel;

    public NioClientHandler2(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);// 非阻塞
            started = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        doConnect();

        while (started) {
            try {
                selector.select(); //阻塞 有事件发生
//                selector.select(1000)
                Set<SelectionKey> keys = selector.selectedKeys();//哪些事件已经触发了拿出来
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();//处理完了移除

                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        key.cancel();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                // 拿到连接 读写事件的注册
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                } else System.exit(-1);

            }

            if (key.isReadable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int size = sc.read(byteBuffer);
                if (size > 0) {
                    byteBuffer.flip();
                    byte[] bs = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bs);
                    String string = new String(bs);

                } else if (size < 0) {
                    key.cancel();
                    sc.close();
                }

            }
            if (key.isWritable()) {
                System.out.println("缓冲区空了可以写了");
                SocketChannel channel = (SocketChannel) key.channel();
                // TODO
                ByteBuffer att = (ByteBuffer) key.attachment();
                if (att.hasRemaining()) {
                    int count = sc.write(att);
                } else {
                    // 写玩了知道读感兴趣
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    /**
     * NIO正确连接
     */
    private void doConnect() {
        // 非阻塞
        try {
            if (socketChannel.connect(new InetSocketAddress(host, port))) {

            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            doWrite(socketChannel, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(SocketChannel socketChannel, String msg) throws IOException {
        byte[] bytes = msg.getBytes();
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        wrap.flip();
        socketChannel.write(wrap); // 写到管道
        // 循环写
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, wrap);
    }
}
