package com.sheldon.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fangxiaodong
 * @date 2021/07/01
 */
public class TreadDemo {

    /*
    * interrupt 只是对这个线程进行标记, 不是实际进行停止的
    * */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()->{
            for (int i = 0; i < 5000; i++){
                System.out.println("Thread : " + i);

                System.out.println(Thread.currentThread().isInterrupted());
                if(Thread.currentThread().isInterrupted()){
                    return;
                }

//                Thread.currentThread().interrupt();
            }
        });
        Thread.sleep(100L);
        service.shutdown();

//        Thread t = new MyThread();
//        t.start();
//
//        t.interrupt();
//        t.stop();
//        System.out.println("MyThread : " + t.interrupted());
//        System.out.println("MyThread : " + t.interrupted());


        while(true){
        }
    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 50; i++){
                System.out.println("MyThread" + i);
            }
        }
    }

}
