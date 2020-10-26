package ch6_io.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO
 * 86150
 * Server
 * 2020/3/6 20:20
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10001);
        System.out.println("server start");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ServerTask(socket)).start();
        }

    }

    public static class ServerTask implements Runnable {
        private Socket socket;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                String name = inputStream.readUTF();
                System.out.println("accept message:" + name);
                //返回给客户端
                outputStream.writeUTF("hello!" + name);
                outputStream.flush();//刷到客户端
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
