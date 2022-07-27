package com.sheldon.jvm.clazzloader;

/**
 * @author fangxiaodong
 * @date 2021/09/23
 */
public class A {

    private static String staticStr = "static";

    private String a;

    public A(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
