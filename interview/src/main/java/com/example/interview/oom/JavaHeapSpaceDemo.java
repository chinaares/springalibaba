package com.example.interview.oom;

/**
 * @author wuhao
 * @desc -Xmx10m -Xms10m -XX:+PrintGCDetails
 * @date 2021-09-10 15:14:50
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
//        String str= "java";
//        while (true){
//            str+=str+new Random().nextInt(111111)+new Random().nextInt(121312);
//            str.intern();
//        }
        //创建大对象
        byte[] bytes=new byte[20*1024*1024];
    }
}
