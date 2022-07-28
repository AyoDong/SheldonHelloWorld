package com.sheldon.jvm.gc.cms.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangxiaodong
 * @date 2021/10/08
 */
public class Demo6 {

    public static void main(String[] args) throws InterruptedException {
        List<Data> datas = new ArrayList<>();
        for (int i = 0; i < 10000; i++){
            datas.add(new Data());
        }
        Thread.sleep(1 * 60 * 60 * 1000);
    }

    static class Data{

    }

}
