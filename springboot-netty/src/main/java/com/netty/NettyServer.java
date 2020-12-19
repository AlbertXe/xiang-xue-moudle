package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 86150
 * NettyServer
 * 2020/12/19 12:41
 */
public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer(9090).start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        NettyServerHandler childHandler = new NettyServerHandler();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class) //NIO channnel传输
                .localAddress(port)
                .childHandler(childHandler);

        new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(childHandler);
            }
        };
        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();

    }
}
