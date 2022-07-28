package com.sheldon.jvm.gc.cms.demo;

/**
 * @author fangxiaodong
 * @date 2021/09/30
 */
public class Demo3 {

    /**
     * -XX:NewSize=10485760
     * -XX:MaxNewSize=10485760
     * -XX:InitialHeapSize=20971520
     * -XX:MaxHeapSize=20971520
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=3145728
     * -XX:+UseParNewGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -Xloggc:gc.log
     *
     * 新生代初始值和新生代的最大值是 10MB, Eden = 8MB, Survivor From = 1MB, Survivor To = 1MB
     * 堆内存初始值和堆内存最大值是 20MB
     * 大对象进入老年代的阈值: 3MB
     *
     * 一开始就创建了一个 4MB 的对象, 这个对象已经超过了直接进入老年代的阈值了, 此时就会直接进入老年代
     * 然后创建了 3 * 2MB = 6MB + 128KB 的对象, 都是有引用的
     *
     * 此时要创建 array6 的时候, 会发现新生代的内存不够, 会触发一次 Minor GC, 但是此时老年代会发现老年代也不够内存去存放 6MB + 128KB, 因为里面已有 4MB 的对象了
     * 此时就会提前触发 Full GC, 这个时候 4MB 就会给清除掉. 然后触发 Minor GC, 这里发现 Survivor 的内存都不够存放 6MB + 128KB, 这个时候就会触发老年代的升级
     * 6MB + 128KB 都会存放到老年代中, 然后 array6 就存进 Eden 区了
     *
     * @param args
     */
    public static void main(String[] args) {
        int mb = 1024 * 1024;
        byte[] array1 = new byte[4 * mb];
        array1 = null;

        byte[] array2 = new byte[2 * mb];
        byte[] array3 = new byte[2 * mb];
        byte[] array4 = new byte[2 * mb];
        byte[] array5 = new byte[128 * 1024];

        byte[] array6 = new byte[2 * mb];
    }
}
