package com.sheldon.jvm.gc.demo;

/**
 * @author fangxiaodong
 * @date 2021/10/09
 */
public class VirtualStackOom {

    /**
     * StackOverflowError
     *
     * JVM 虚拟机栈可以根据 -Xss 进行设置
     * 假设这里是 1MB
     *
     * 这里每进行方法的操作的时候, 就会创建一个栈帧, 因此这里初始的时候会有 main 方法的栈帧
     * 然后递归的调用 sayHello, 就会一直把 sayHello 的栈帧压入虚拟机栈中, 会慢慢的增加内存, 如果超过了最大的内存容量, 就会发生 OOM
     *
     * @param args
     */
    public static void main(String[] args) {
        sayHello();
    }

    private static void sayHello(){
        sayHello();
    }

}
