package com.example.netty.config;

import com.example.netty.handler.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author WH
 * <p>
 * netty服务初始化器
 **/
@NoArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    // SSL认证
    private SslContext sslContext;

    public ServerChannelInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        if (sslContext != null) {
            socketChannel.pipeline().addLast(sslContext.newHandler(socketChannel.alloc()));
        }
        socketChannel.pipeline()
                .addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))//心跳机制，读空闲，写空闲满足条件调用userEventTriggered
//                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0))//在消息头中定义长度字段，来标识消息的总长度解决TCP粘包黏包问题
                .addLast("http-codec", new HttpServerCodec())//websocket基于http协议，使用http编解码器
                .addLast("http-chunked", new ChunkedWriteHandler())//用于大数据的分区传输，支持异步发送大的码流(大的文件传输),但不占用过多的内存，防止java内存溢出
//                .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))//添加编解码
//                .addLast("encoder", new ObjectEncoder())
//                .addLast("decoder", new StringDecoder())
//                .addLast("encoder", new StringEncoder())
                //http数据在传输过程中是分段的
                .addLast(new HttpObjectAggregator(512*1024))// 聚合器，HTTP 消息的合并处理,将请求,响应信息一次接收完
                //websocket是以帧（Frame）的形式传递
                //请求形式 ws://localhost:8080/XXX
                //把http协议升级为websocket协议，保持长连接
                .addLast(new WebSocketServerProtocolHandler("/"))
                .addLast(new NettyServerHandler());//自定义handler处理心跳机制触发的userEventTriggered
    }
}