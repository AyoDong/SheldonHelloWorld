package com.sheldon.sync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author fangxiaodong
 * @date 2021/03/30
 */
public class MyBlockingQueue {

    private static BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    private static void putQueue(){
        int i = 0;
        while(true){
            queue.offer(i);
        }
    }

    private static void getQueue(){
        while(true){
            Integer i = queue.poll();
            while(i == 0){
                System.out.println(queue.size());
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()-> putQueue());
        service.submit(()-> getQueue());
        while(true){

        }
    }

}
