package com.example.interview;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-07 16:27:30
 */
public class ProdConsumer {
    public static void main(String[] args) {
        ShareDate shareDate=new ShareDate();

        new Thread(()->{
            for (int i = 1; i < 5; i++) {
                try {
                    shareDate.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 1; i < 5; i++) {
                try {
                    shareDate.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BBB").start();

        new Thread(()->{
            for (int i = 1; i < 5; i++) {
                try {
                    shareDate.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CCC").start();

        new Thread(()->{
            for (int i = 1; i < 5; i++) {
                try {
                    shareDate.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"DDD").start();

    }
}

class ShareDate {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @SneakyThrows
    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
    @SneakyThrows
    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}
