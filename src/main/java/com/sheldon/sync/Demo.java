package com.sheldon.sync;

/**
 * @author fangxiaodong
 * @date 2021/03/23
 */
public class Demo {

    private static Boolean isOK = false;

    private static void m(){
        System.out.println("m start");
        while(true){
            synchronized (isOK){
                if(isOK){
                    System.out.println("---OK");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        new Thread(Demo::m).start();
        new Thread(()->{
            m();
        }).start();

        //一定要有这个睡眠, 确保线程已经开启
        Thread.sleep(10);
        isOK = true;
        while(true){

        }
    }
}
