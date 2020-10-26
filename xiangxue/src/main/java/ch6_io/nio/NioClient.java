package ch6_io.nio;

import java.util.Scanner;

/**
 * 86150
 * NioClient
 * 2020/3/6 23:21
 */
public class NioClient {
    private static NioClientHandler nioClientHandler;
    private static String host = "127.0.0.1";
    private static int port = 10000;

    public static void start() {
        if (nioClientHandler != null) {
            nioClientHandler.stop();
        }
        nioClientHandler = new NioClientHandler(host, port);
        new Thread(nioClientHandler, "server").start();
    }

    public static boolean sendMsg(String msg) {
        nioClientHandler.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) {
        start();
        Scanner scanner = new Scanner(System.in);
        while (sendMsg(scanner.nextLine())) ;
    }
}
