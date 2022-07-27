package com.sheldon.thread.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fangxiaodong
 * @date 2021/10/11
 */
public class AQSDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(3);
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 3; i++){
            service.submit(()->{
                try{
                    lock.lock();
                    business();
                } finally {
                    lock.unlock();
                }
            });
        }

        Thread.sleep(1000);
    }

    private static void business(){
        Thread.interrupted();
        System.out.println("business");
    }

}
