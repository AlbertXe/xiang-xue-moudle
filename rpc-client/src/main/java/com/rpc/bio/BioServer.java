package com.rpc.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 21:54
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9090));

        while (true) {
            new Thread(new ServerTask(serverSocket.accept())).start();
        }

    }

    static class ServerTask implements Runnable {
        private Socket socket;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

                // 读写事件
                String name = is.readUTF();
                os.writeObject("hello limit");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
