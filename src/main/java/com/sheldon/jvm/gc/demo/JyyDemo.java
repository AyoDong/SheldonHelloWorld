package com.sheldon.jvm.gc.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangxiaodong
 * @date 2021/12/07
 */
public class JyyDemo {

    public static void main(String[] args) throws InterruptedException {
        List<String> strs = new ArrayList<>();

        System.out.println("-- start --");

        char c = 'a';
        int i = 0;
        while(true){
            if(i < 10){
                strs.add(String.valueOf(c));
            }else{
                Thread.sleep(1000);
                i = 0;
                c++;
            }
        }

    }

}
