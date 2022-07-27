package com.sheldon.thread;

/**
 * @author fangxiaodong
 * @date 2021/10/27
 */
public class Visibility {

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        Thread t1 = new Thread();
        t1.start();

        Thread.sleep(10);
        System.out.println(i);
    }

}
