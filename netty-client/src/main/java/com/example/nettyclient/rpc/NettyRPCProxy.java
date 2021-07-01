package com.example.nettyclient.rpc;

import com.example.common.exception.basic.APIResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.SneakyThrows;

//客户端代理类
public class NettyRPCProxy {
    //根据接口创建代理对象
    @SneakyThrows
    public  APIResponse create(String message) {
        //开始用 Netty 发送数据
        EventLoopGroup group = new NioEventLoopGroup();
        ResultHandler resultHandler = new ResultHandler();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //解码器
                            pipeline.addLast("decoder",  new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            //客户端业务处理类
                            pipeline.addLast("handler", resultHandler);
                        }
                    });
            ChannelFuture future = b.connect("127.0.0.1", 9090).sync();
            future.channel().writeAndFlush(message).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return resultHandler.getResponse();
    }
}