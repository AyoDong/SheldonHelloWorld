package com.sheldon.jvm.gc.demo;

/**
 * @author fangxiaodong
 * @date 2021/09/30
 */
public class Demo1 {

    /**
     * -XX:NewSize=5242880
     * -XX:MaxNewSize=5242880
     * -XX:InitialHeapSize=10485760
     * -XX:MaxHeapSize=10485760
     * -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=10485760
     * -XX:+UseParNewGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -Xloggc:gc.log
     *
     * 新生代初始值和新生代的最大值是 5MB, Eden = 4MB, Survivor From = 0.5MB, Survivor To = 0.5MB
     * 堆内存初始值和堆内存最大值是 10MB
     * 大对象进入老年代的阈值: 10MB
     *
     * 这里会创建 3MB 的对象, 最后 array 不进行引用, 而在创建 2MB 的 array2 的时候, 会进行一次 GC
     *
     * Java HotSpot(TM) 64-Bit Server VM (25.261-b12) for windows-amd64 JRE (1.8.0_261-b12), built on Jun 18 2020 06:56:32 by "" with unknown MS VC++:1916
     * Memory: 4k page, physical 16580960k(5658540k free), swap 33358176k(15849368k free)
     * CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
     * 0.155: [GC (Allocation Failure) 0.155: [ParNew: 3826K->512K(4608K), 0.0018004 secs] 3826K->1697K(9728K), 0.0020214 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     * Heap
     *  par new generation   total 4608K, used 3746K [0x00000000ff600000, 0x00000000ffb00000, 0x00000000ffb00000)
     *   eden space 4096K,  78% used [0x00000000ff600000, 0x00000000ff9288f0, 0x00000000ffa00000)
     *   from space 512K, 100% used [0x00000000ffa80000, 0x00000000ffb00000, 0x00000000ffb00000)
     *   to   space 512K,   0% used [0x00000000ffa00000, 0x00000000ffa00000, 0x00000000ffa80000)
     *  concurrent mark-sweep generation total 5120K, used 1185K [0x00000000ffb00000, 0x0000000100000000, 0x0000000100000000)
     *  Metaspace       used 3268K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 354K, capacity 388K, committed 512K, reserved 1048576K
     *
     * @param args
     */
    public static void main(String[] args) {
        int mb = 1024 * 1024;
        byte[] array1 = new byte[mb];
        array1 = new byte[mb];
        array1 = new byte[mb];
        array1 = null;
        byte[] array2 = new byte[2 * mb];
    }
}
