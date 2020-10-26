package ch6_io.nio;

/**
 * 86150
 * NioServer
 * 2020/3/7 13:19
 */
public class NioServer {
    private static int port = 10000;
    private static NioServerHandler nioServerHandler;

    public static void start() {
        if (nioServerHandler != null) {
            nioServerHandler.stop();
        }
        nioServerHandler = new NioServerHandler(port);
        new Thread(nioServerHandler, "server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
