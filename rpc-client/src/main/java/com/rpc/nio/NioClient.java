package com.rpc.nio;

import java.util.Scanner;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 23:23
 */
public class NioClient {
    private static NioClientHandler nioClientHandler;

    public static void main(String[] args) {
        start();
        Scanner scanner = new Scanner(System.in);
        while (sendMsg(scanner.next())) {

        }
    }

    private static void start() {
        if (nioClientHandler != null) {
            nioClientHandler.stop();
        }
        nioClientHandler = new NioClientHandler("", 9090);
        new Thread(nioClientHandler, "server").start();
    }

    public static boolean sendMsg(String msg) {
        nioClientHandler.sendMsg(msg);
        return true;
    }
}
