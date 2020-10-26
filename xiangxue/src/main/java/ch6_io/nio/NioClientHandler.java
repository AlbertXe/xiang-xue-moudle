package ch6_io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 86150
 * NioClient
 * 2020/3/6 22:44
 */
public class NioClientHandler implements Runnable {
    private String host;
    private int port;
    private static volatile boolean flag;
    private Selector selector;
    private SocketChannel socketChannel;

    public NioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);//非阻塞模式
            flag = true;
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
        //连接
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        while (flag) {
            try {
                selector.select();//阻塞方法  有事件发生就往下走  timeout
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //必须要用迭代器 要remove
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();

            if (key.isConnectable()) {//处理连接事件
                if (socketChannel.finishConnect()) {//channel完成连接  阻塞方法
                    socketChannel.register(selector, SelectionKey.OP_READ);//只要缓冲区有数据就会被触发事件
                    //     **这个关注和覆盖前一个事件**
                } else System.exit(-1);
            }
            if (key.isReadable()) {//处理读事件
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readed = socketChannel.read(byteBuffer);// channel角度:channel写到buffer
                if (readed > 0) {
                    //TODO
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String result = new String(bytes, "UTF-8");
                    System.out.println("server:" + result);
                } else if (readed < 0) {
                    key.cancel();
                    socketChannel.close();
                }
            }
//            if (key.isWritable()) {
//                System.out.println("writable .........");
//                SocketChannel channel = (SocketChannel) key.channel();
//                ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
//                if (byteBuffer.hasRemaining()) {
//                    int count = channel.write(byteBuffer);
//                    System.out.println("write " + count + " byte! hsaRemaining=" + byteBuffer.hasRemaining());
//
//                }else {
//                    key.interestOps(SelectionKey.OP_READ);//写完就不写了
//                }
//
//            }
        }
    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {//非阻塞方法

        } else {
            //告诉 selector关注连接事件
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT);

        }
    }

    public void sendMsg(String msg) {
        try {
            NioUtils.doWrite(selector, socketChannel, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
