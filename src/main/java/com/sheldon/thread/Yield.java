package com.sheldon.thread;

/**
 * @author fangxiaodong
 * @date 2021/10/09
 */
public class Yield {

    public static void main(String[] args) {
        System.out.println("---");
        Thread.yield();
        System.out.println("---");
    }

}
