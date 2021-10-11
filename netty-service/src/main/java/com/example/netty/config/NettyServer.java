package com.example.netty.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Gjing
 * <p>
 * 服务启动监听器
 **/
@Slf4j
@Service
public class NettyServer {

    @Autowired
    private NettyProperties nettyProperties;

    @SneakyThrows
    public void start() {
        log.info("初始化netty线程组");
        final int workgroupCoreSize = Optional.ofNullable(nettyProperties.getWorkGroupThreads()).orElse(200);
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        ServerBootstrap bootstrap = new ServerBootstrap();
        SslContext sslContext=null;

        if (checkIsWindows()) {
            // Nio 线程组
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup(workgroupCoreSize);
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class);
        } else {
            // Epoll
            bossGroup = new EpollEventLoopGroup();
            workerGroup = new EpollEventLoopGroup(workgroupCoreSize);
            bootstrap.group(bossGroup, workerGroup)
                    .channel(EpollServerSocketChannel.class);

        }

        //开启ssl端口
        if (nettyProperties.getOpenSSL()) {
            sslContext = buildSelfContext();
            log.info("使用自签名启动https");
        }

        bootstrap.localAddress(new InetSocketAddress(nettyProperties.getPort()))
                .option(ChannelOption.SO_BACKLOG, nettyProperties.getSobacklog()) //设置线程队列中等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //保持活动连接状态
                .handler(new LoggingHandler(LogLevel.INFO))//设置log监听器，并且日志级别为INFO，方便观察运行流程
                .childHandler(serverChannelInitializer(sslContext))
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        // 绑定端口
        try {
            ChannelFuture channelFuture = bootstrap.bind(nettyProperties.getPort()).sync();
            log.info("HttpServer started and listen on " + channelFuture.channel().localAddress());
            // 等待连接被关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    private boolean checkIsWindows() {
        return Optional.ofNullable(System.getProperty("os.name")).orElse("linux").toLowerCase().startsWith("windows");
    }

    @SneakyThrows
    private SslContext buildSelfContext() {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
    }

    private ServerChannelInitializer serverChannelInitializer(SslContext sslContext){
        if(Objects.nonNull(sslContext)){
            return new ServerChannelInitializer(sslContext);
        }else{
            return new ServerChannelInitializer();
        }
    }
}