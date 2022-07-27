package com.sheldon.jvm.gc;

/**
 * @author fangxiaodong
 * @date 2021/09/23
 */
public class GCTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while(true){
            loadGarbage();
            Thread.sleep(5 * 1000);
            System.out.println(i++);
        }
    }

    public static void loadGarbage(){
        Garbage garbage = null;
        for (int i = 0; i < 10000; i++){
            garbage = new Garbage();
        }
        garbage.printShit();
    }

}
