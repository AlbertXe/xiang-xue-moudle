package ch6_io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 86150
 * NettyServer
 * 2020/3/7 20:22
 */
public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServer server = new NettyServer(9999);
        System.out.println("server start");
        server.start();
        System.out.println("server close");
    }

    private void start() throws InterruptedException {
        NettyServerHandler nettyServerHandler = new NettyServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group).channel(NioServerSocketChannel.class).localAddress(port)
                    .childHandler(nettyServerHandler);
            //多个处理器的时候用
            new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));//换行
                    channel.pipeline().addLast(nettyServerHandler);
                }
            };

            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}
