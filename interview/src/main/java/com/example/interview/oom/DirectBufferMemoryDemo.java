package com.example.interview.oom;

import java.nio.ByteBuffer;

/**
 * @author wuhao
 * @desc -Xmx20m -Xms10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * @date 2021-09-10 15:38:57
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的DirectMemory"+(sun.misc.VM.maxDirectMemory()/(double)1024/1024)+"mb");
        ByteBuffer bf=ByteBuffer.allocateDirect(6*1024*1024);
    }
}
