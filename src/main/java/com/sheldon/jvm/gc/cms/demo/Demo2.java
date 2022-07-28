package com.sheldon.jvm.gc.cms.demo;

/**
 * @author fangxiaodong
 * @date 2021/09/30
 */
public class Demo2 {

    /**
     * -XX:NewSize=10485760
     * -XX:MaxNewSize=10485760
     * -XX:InitialHeapSize=20971520
     * -XX:MaxHeapSize=20971520
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=10485760
     * -XX:+UseParNewGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -Xloggc:gc.log
     *
     * 新生代初始值和新生代的最大值是 10MB, Eden = 8MB, Survivor From = 1MB, Survivor To = 1MB
     * 堆内存初始值和堆内存最大值是 20MB
     * 大对象进入老年代的阈值: 10MB
     *
     * 到了创建 array3 的时候, 新生代的占用情况是 Eden 3 * 2MB = 6MB + 128KB + 600KB(未知对象) 的内容
     * 此时就会触发一次 Minor GC, 然后 array2(128KB) + 600KB 就会进入 Survivor 区, 此时他们的年龄是 1
     *
     * 然后到了 array4 的时候, Eden 区中有 3 * 2MB = 6MB + 128KB + n(未知对象)
     * 此时又会触发一次 Minor GC, 但是这个时候, 原本在 Survivor 区的 128KB + 600KB 的内存超过了 Survivor 区的 50% 了
     * 此时就会就触发了动态升级到老年代了, 就会把里面年龄大的那批数据升级到老年代, 就是说 128KB + 600KB 的内存(1岁), 放入老年代
     *
     * @param args
     */
    public static void main(String[] args) {
        int mb = 1024 * 1024;
        byte[] array1 = new byte[2 * mb];
        array1 = new byte[2 * mb];
        array1 = new byte[2 * mb];
        array1 = null;

        byte[] array2 = new byte[128 * 1024];
        byte[] array3 = new byte[2 * mb];

        array3 = new byte[2 * mb];
        array3 = new byte[2 * mb];
        array3 = new byte[128 * 1024];
        array3 = null;

        byte[] array4 = new byte[2 * mb];
    }
}
