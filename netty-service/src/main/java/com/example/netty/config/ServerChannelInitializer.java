package com.example.netty.config;

import com.example.netty.handler.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import lombok.NoArgsConstructor;

/**
 * @author WH
 *
 * netty服务初始化器
 **/
@NoArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    // SSL认证
    private SslContext sslContext;

    public ServerChannelInitializer(SslContext sslContext){
        this.sslContext = sslContext;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel){
        if (sslContext != null) {
            socketChannel.pipeline().addLast(sslContext.newHandler(socketChannel.alloc()));
        }
        //添加编解码
        socketChannel.pipeline().addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                ClassResolvers.cacheDisabled(null)));
        socketChannel.pipeline().addLast("encoder", new ObjectEncoder());
        // 将请求,响应信息一次接收完
//        socketChannel.pipeline().addLast(new HttpObjectAggregator(maxContentLength * 1024));// HTTP 消息的合并处理
        socketChannel.pipeline().addLast(new NettyServerHandler());
    }
}