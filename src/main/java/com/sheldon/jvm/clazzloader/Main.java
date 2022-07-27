package com.sheldon.jvm.clazzloader;

/**
 * @author fangxiaodong
 * @date 2021/09/23
 */
public class Main {

    public static void main(String[] args) {
        A a = new A("A");
        A a1 = new A("A");
        B b = new B("B");

        System.out.println(a.getA());
        System.out.println(b.getB());
    }
}
