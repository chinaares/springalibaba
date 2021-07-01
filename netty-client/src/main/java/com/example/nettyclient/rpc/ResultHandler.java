package com.example.nettyclient.rpc;

import com.example.common.exception.basic.APIResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

//客户端业务处理类
@Data
public class ResultHandler extends ChannelInboundHandlerAdapter {
    private APIResponse response;


    @Override
    //读取服务器端返回的数据(远程调用的结果)
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (APIResponse)msg;
        ctx.close();
    }
}