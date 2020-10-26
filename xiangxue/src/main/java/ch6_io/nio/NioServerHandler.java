package ch6_io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 86150
 * NioServerHandler
 * 2020/3/7 12:54
 */
public class NioServerHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static volatile boolean flag;

    public NioServerHandler(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);//开启非阻塞

            serverSocketChannel.socket().bind(new InetSocketAddress(port));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            flag = true;
            System.out.println("服务已经启动,端口号:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void stop() {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                selector.select();//阻塞
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        closeSelectionKey(key);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeSelector();

    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {//处理新接入请求
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                System.out.println("服务器接收到连接");
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {//处理读
                System.out.println("通道连接成功,可以读数据.");
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readed = channel.read(byteBuffer);
                if (readed > 0) {
                    byteBuffer.flip();
                    byte[] bs = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bs);
                    String msg = new String(bs, "utf-8");
                    msg = response(msg);
                    NioUtils.doWrite(selector, channel, msg);
                } else if (readed < 0) {
                    key.cancel();
                    channel.close();
                }
            }
            if (key.isWritable()) {
                System.out.println("writable .........");
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                if (byteBuffer.hasRemaining()) {
                    int count = channel.write(byteBuffer);
                    System.out.println("write " + count + " byte! hsaRemaining=" + byteBuffer.hasRemaining());

                } else {
                    key.interestOps(SelectionKey.OP_READ);//写完就不写了
                }

            }
        }
    }

    private String response(String msg) {
        msg = "client:" + msg;
        System.out.println(msg);
        return msg;
    }

    private void closeSelector() {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeSelectionKey(SelectionKey key) {
        if (key != null) {
            key.channel();
            if (key.channel() != null) {
                try {
                    key.channel().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
