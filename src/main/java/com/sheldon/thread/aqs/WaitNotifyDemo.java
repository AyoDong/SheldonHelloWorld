package com.sheldon.thread.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fangxiaodong
 * @date 2021/10/27
 */
public class WaitNotifyDemo {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        WaitNotifyDemo obj = new WaitNotifyDemo();
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++){
            service.submit(()->{
                try {
                    demo(obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
    }

    private static void demo(Object obj) throws InterruptedException {
        synchronized (obj){
            if(count++ == 0){
                obj.wait();
            }else{
                obj.notify();
            }
            System.out.println("-- after wait -- " + Thread.currentThread().getName() + " --");
        }
    }

}
