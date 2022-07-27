package com.sheldon.jvm.gc.demo;

import com.sheldon.jvm.gc.Garbage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangxiaodong
 * @date 2021/10/08
 */
public class Demo7 {

    public static void main(String[] args) {
        int cnt = 0;
        List<Garbage> garbages = new ArrayList<>();
        while(true){
            System.out.println(cnt++);
            garbages.add(new Garbage());
        }
    }

}
