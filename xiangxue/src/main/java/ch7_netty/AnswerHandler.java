package ch7_netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * 86150
 * AnswerHandler
 * 2020/3/10 20:54
 */
public class AnswerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String s = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println(s);
        //应答
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("床前明月光", CharsetUtil.UTF_8), msg.sender()));
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
