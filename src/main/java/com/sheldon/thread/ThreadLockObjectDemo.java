package com.sheldon.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author fangxiaodong
 * @date 2021/10/09
 */
public class ThreadLockObjectDemo {

    public static void main(String[] args) {

        Object obj = new Object();

        Thread t1 = new Thread(() -> {
            while(true){
                synchronized (obj){
                    System.out.println("--synchronized--" + Thread.currentThread().getName());
                    LockSupport.unpark(Thread.currentThread());
                    LockSupport.park();//不会放弃锁
//                    LockSupport.parkUntil(1000 * 60 * 60);
//                    try {
//                        obj.wait(1000L);//释放锁
//                        Thread.sleep(1000L);
//                        Thread.currentThread().join(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("--synchronized--after--sleep--" + Thread.currentThread().getName());
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while(true){
                synchronized (obj){
                    System.out.println("--synchronized--" + Thread.currentThread().getName());
//                    LockSupport.parkNanos(10000L);
//                    LockSupport.parkUntil(1000 * 60 * 60);
                    try {
//                        obj.wait(1000L);
//                        Thread.sleep(1000L);
                        Thread.currentThread().join(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("--synchronized--after--sleep--" + Thread.currentThread().getName());
                }
            }
        });
//        t2.start();

        while(true){
            LockSupport.unpark(t1);
        }
    }

}
