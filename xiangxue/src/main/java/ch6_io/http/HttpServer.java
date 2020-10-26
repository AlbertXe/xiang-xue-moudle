package ch6_io.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.security.cert.CertificateException;

/**
 * 86150
 * HttpServer
 * 2020/3/8 21:51
 */
public class HttpServer {
    private static int port = 6789;
    private static EventLoopGroup group = new NioEventLoopGroup();
    private static ServerBootstrap b = new ServerBootstrap();
    private static final boolean ssl = false;

    public static void main(String[] args) throws CertificateException {
        SslContext sslContext = null;
        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
//            sslContext = SslContextBuilder
        }
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ServerHandlerInit(sslContext));
            ChannelFuture future = b.bind(port).sync();
            System.out.println("服务器启动成功,端口是:" + port);
            future.channel().closeFuture().sync();//关闭监听
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
