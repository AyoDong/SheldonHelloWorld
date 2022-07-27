package com.sheldon.interator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author fangxiaodong
 * @date 2021/07/08
 */
public class IteratorDemo {

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        IteratorDemo demo = new IteratorDemo();
        demo.setList(list);

        System.out.println(demo.getList());

        Iterator<String> iterator = demo.getList().iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("a")){
                iterator.remove();
            }
        }

        System.out.println(demo.getList());
    }
}
