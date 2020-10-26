package ch7_netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * UDP
 * 86150
 * UdpQuestion
 * 2020/3/10 20:37
 */
public class UdpQuestion {

    public void run(int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .handler(new QuestionHandler());

        try {
            Channel channel = bootstrap.bind(0).sync().channel();
            //无需连接
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("请回答古诗", CharsetUtil.UTF_8)
                    , new InetSocketAddress("127.0.0.1", port))).sync();

            //等待15秒后 关闭通信
            if (!channel.closeFuture().await(15000)) {
                System.out.println("超时");
            }

        } catch (InterruptedException e) {
            group.shutdownGracefully();
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new UdpQuestion().run(8080);
    }
}
