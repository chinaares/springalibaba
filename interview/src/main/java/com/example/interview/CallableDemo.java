package com.example.interview;


import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-08 13:55:29
 */
class MyThread implements Callable<Integer> {
    @Override
    @SneakyThrows
    public Integer call() {
        System.out.println(Thread.currentThread().getName() +"调用返回值");
        TimeUnit.SECONDS.sleep(2L);
        return 1024;
    }


}

public class CallableDemo {
    @SneakyThrows
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "AAA").start();
        System.out.println(Thread.currentThread().getName() + "***************");
        while (!futureTask.isDone()) {
            TimeUnit.SECONDS.sleep(1L);
            System.out.println("我在等返回值");
        }
        System.out.println("返回值:" + futureTask.get());
    }
}
