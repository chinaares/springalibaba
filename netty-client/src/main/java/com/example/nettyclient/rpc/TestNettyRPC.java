package com.example.nettyclient.rpc;

import cn.hutool.json.JSONUtil;
import com.example.common.exception.basic.APIResponse;

//服务调用方
public class TestNettyRPC {
    public static void main(String[] args) {
        //第 1 次远程调用
        NettyRPCProxy nettyRPCProxy=new NettyRPCProxy();
        APIResponse response = nettyRPCProxy.create("我就传点东西");
        System.out.println("获取返回值"+JSONUtil.parseObj(response));
    }
}