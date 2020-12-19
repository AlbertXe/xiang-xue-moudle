package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 86150
 * NettyClient
 * 2020/12/19 11:37
 */
public class NettyClient {
    private String host;
    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void start() throws InterruptedException {
        // 线程组
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .handler(new NettyHandler());
        try {
            // 连接远程 阻塞直到连接完成
            ChannelFuture future = bootstrap.connect().sync();

            // 阻塞直到 channel close
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            group.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new NettyClient("127.0.0.1", 9090).start();
    }

}
