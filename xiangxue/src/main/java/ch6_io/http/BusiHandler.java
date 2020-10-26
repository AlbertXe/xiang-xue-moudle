package ch6_io.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * 86150
 * BusiHandler
 * 2020/3/8 21:32
 */
public class BusiHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String resutl = "";
        FullHttpRequest request = (FullHttpRequest) msg;
        System.out.println(request.headers());
        String uri = request.uri();
        String content = request.content().toString(CharsetUtil.UTF_8);
        HttpMethod method = request.method();
        if (!"/test".equals(uri)) {
            resutl = "非法请求！" + uri;
            send(ctx, resutl, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        System.out.println("接收到请求:" + method);
        if (HttpMethod.GET == method) {
            System.out.println("body:" + content);
            resutl = "GET应答:" + "";
            send(ctx, resutl, HttpResponseStatus.OK);
        }
        if (HttpMethod.POST == method) {

        }


    }

    private void send(ChannelHandlerContext ctx, String resutl, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(resutl, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//写完关闭连接
    }
}
