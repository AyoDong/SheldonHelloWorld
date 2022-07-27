package com.sheldon.jvm.gc.demo;

/**
 * @author fangxiaodong
 * @date 2021/10/13
 */
public class Demo8 {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10 * 1000);
        while(true){
            byte[] bytes = new byte[1024 * 1024];
        }
    }
    //-XX:NewSize=20971520
    //-XX:MaxNewSize=20971520
}
