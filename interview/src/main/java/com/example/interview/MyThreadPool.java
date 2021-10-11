package com.example.interview;

import java.util.concurrent.*;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-08 14:46:46
 */
public class MyThreadPool {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService= Executors.newSingleThreadExecutor();
//        ExecutorService executorService= Executors.newCachedThreadPool();
//        ExecutorService executorService= Executors.newScheduledThreadPool(5);

        //cpu密集型最大线程数：cpu核数+1
        //io密集型：
        // 1.不是一直执行任务：cpu核数*2
        // 2.线程阻塞：cpu核数/(1-阻塞系数） 阻塞系数在0.8-0.9之间
        //   比如8核：8/(1-0.9)=80
        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        ThreadPoolInit(executorService);
    }

    private static void ThreadPoolInit(ExecutorService executorService) {

        try {
            for (int i = 1; i <= 8; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
