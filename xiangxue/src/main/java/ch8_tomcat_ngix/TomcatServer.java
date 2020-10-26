package ch8_tomcat_ngix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://localhost:8080/ki12
 * 86150
 * TomcatServer
 * 2020/3/14 22:39
 */
public class TomcatServer {

    private static ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
        //0 加载
        init();

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("server init");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            service.execute(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg = null;
                    StringBuilder sb = new StringBuilder();
                    while ((msg = reader.readLine()) != null) {
                        sb.append(msg).append("\r\n");
                    }
                    System.out.println(sb);

                    OutputStream outputStream = socket.getOutputStream();
                    byte[] bs = "albert,hello".getBytes();
                    outputStream.write("HTTP/1.1 200 OK \r\n".getBytes());
//                    outputStream.write("Content-Type:text/html;charset=UTF-8\r\n".getBytes());
                    outputStream.write(("Content-Length:" + bs.length + "\r\n").getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.write(bs);
                    outputStream.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        }
    }

    private static void init() {
    }
//    HTTP/1.1 200 OK
//    Server: openresty
//    Date: Sat, 14 Mar 2020 15:16:33 GMT
//    Content-Type: text/html; charset=UTF-8
//    Transfer-Encoding: chunked
//    Connection: keep-alive
//    Vary: Accept-Encoding
//    Set-Cookie: QiHooGUID=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; Max-Age=0; path=/
//    Set-Cookie: QiHooGUID=AB71A768D2D46B61CE69EEBBDB660F86.1573652831085; expires=Mon, 14-Mar-2022 15:16:33 GMT; Max-Age=63072000; path=/; domain=so.com
//    Expires: Thu, 19 Nov 1981 08:52:00 GMT
//    Cache-Control: no-store, no-cache, must-revalidate
//    Pragma: no-cache
//    Content-Encoding: gzip

}




