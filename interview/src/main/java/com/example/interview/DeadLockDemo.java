package com.example.interview;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-09 09:52:11
 */
class HoldLockThread implements Runnable{
    private String lockOne;
    private String lockTwo;

    public HoldLockThread(String lockOne, String lockTwo) {
        this.lockOne = lockOne;
        this.lockTwo = lockTwo;
    }

    @Override
    @SneakyThrows
    public void run(){
        synchronized (lockOne){
            System.out.println(Thread.currentThread().getName()+"自己获取"+lockOne+"尝试获取"+lockTwo);
            TimeUnit.SECONDS.sleep(2L);
            synchronized (lockTwo){
                System.out.println(Thread.currentThread().getName()+"自己获取"+lockTwo+"尝试获取"+lockOne);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
    }
}
