package com.sheldon.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangxiaodong
 * @date 2021/07/26
 */
public class ThreadSleepDemo {

    private AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        ThreadSleepDemo threadSleepDemo = new ThreadSleepDemo();
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++){
            service.submit(()-> {
                try {
                    threadSleepDemo.sleep();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        while(!service.isShutdown()){
            service.shutdown();
        }
    }

    private synchronized void sleep() throws InterruptedException {
        int i = integer.getAndIncrement();
        if(i < 4){
            this.wait();
            System.out.println("hello, wake up : " + i);
        }else{
            this.notify();
        }
    }

}
