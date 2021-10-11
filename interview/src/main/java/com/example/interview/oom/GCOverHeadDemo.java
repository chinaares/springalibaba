package com.example.interview.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhao
 * @desc -Xmx20m -Xms10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * GC overhead limit exceeded
 * @date 2021-09-10 15:24:48
 */
public class GCOverHeadDemo {
    public static void main(String[] args) {
        int i=0;
        List<String> list=new ArrayList<>();
        while (true){
            list.add(String.valueOf(++i).intern());
        }
    }
}
