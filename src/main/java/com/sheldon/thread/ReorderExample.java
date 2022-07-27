package com.sheldon.thread;

/**
 * @author fangxiaodong
 * @date 2021/10/28
 */
public class ReorderExample {

    private int i = 0;
    private boolean flag = false;

    /**
     * 如果有两个线程分别去调用 writer, reader
     * 操作 1 和 2 没有数据依赖, 操作 3 和 4 也没有数据依赖, 因此可能有数据重排序引发的问题
     * 这个时候, 操作 4 执行的时候, 可能操作 1 都没执行(因为重排序)
     */

    public void writer(){
        i = 1; // 1
        flag = true; // 2
    }

    public void reader(){
        if(flag){ // 3
            int a = i * i; // 4
        }
    }

}
