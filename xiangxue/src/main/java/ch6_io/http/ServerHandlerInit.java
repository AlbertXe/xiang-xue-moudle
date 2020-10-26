package ch6_io.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * http
 * 86150
 * ServerHandlerInit
 * 2020/3/8 21:26
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {
    private SslContext sslContext;

    public ServerHandlerInit(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {//ssl支持
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
        pipeline.addLast("encode", new HttpResponseEncoder());//返回编码
        pipeline.addLast("decode", new HttpRequestDecoder());//请求解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));//解码吼聚合
        pipeline.addLast("compressor", new HttpContentCompressor());//压缩
        pipeline.addLast("handler", new BusiHandler());//业务

    }
}
