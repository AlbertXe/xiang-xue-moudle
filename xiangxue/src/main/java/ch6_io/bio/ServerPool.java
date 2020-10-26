package ch6_io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 伪异步IO  多线程
 * 86150
 * ServerPool
 * 2020/3/6 20:44
 */
public class ServerPool {
    static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10001);
        System.out.println("server start");
        try {
            while (true) {
                service.execute(new Server.ServerTask(serverSocket.accept()));
            }
        } finally {
            serverSocket.close();
        }
    }
}
