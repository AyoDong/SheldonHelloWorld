package com.sheldon.thread.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author fangxiaodong
 * @date 2021/10/26
 */
public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++){
            service.submit(()->{
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }

        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
    }

}
