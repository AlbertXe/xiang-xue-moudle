package ch6_io.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 86150
 * Client
 * 2020/3/6 20:33
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        ObjectOutputStream os = null;
        ObjectInputStream in = null;

        //服务器地址
        SocketAddress address = new InetSocketAddress("127.0.0.1", 10001);
        socket.connect(address);

        os = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        os.writeUTF("albert");
        os.flush();

        System.out.println(in.readUTF());


    }
}
