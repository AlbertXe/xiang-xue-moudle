package ch7_netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * UDP
 * 86150
 * UdpQuestion
 * 2020/3/10 20:37
 */
public class UdpAnswer {
    private static final String ans = "古诗来了";

    public void run(int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .handler(new AnswerHandler());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("应答服务器启动");
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            group.shutdownGracefully();
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new UdpAnswer().run(8080);
    }
}
