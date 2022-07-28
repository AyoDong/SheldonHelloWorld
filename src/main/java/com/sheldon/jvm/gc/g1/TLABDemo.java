package com.sheldon.jvm.gc.g1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sheldon
 * @create: 2022-07-27 11:33
 **/

public class TLABDemo {

    private static final List<String> strs = new ArrayList<>();

    /**
     * 堆内存的大小设置为 128M, G1 本身 region 最小都要 1M
     * -XX:InitialHeapSize=128M
     * -XX:MaxHeapSize=128M
     * -XX:+UseG1GC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -XX:+PrintTLAB 打印 TLAB 相关的东西
     * -XX:+UnlockExperimentalVMOptions 实验选项, finest 是实验选项, 因此要开启这个参数
     * -XX:G1LogLevel=finest 打印日志详细级别, finest 最详细
     * -XX:MaxGCPauseMillis=20
     * -Xloggc:gc.log
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        for (int n = 0;;n++){
            for (int i = 0; i < 100; i++){
                for (int j = 0; j < 10; j++){
                    strs.add("NO." + j + "Str");
                }
            }
            System.out.println("第" + n + "次循环");
            Thread.sleep(100);
        }
    }

    /**
     * Java HotSpot(TM) 64-Bit Server VM (25.271-b09) for windows-amd64 JRE (1.8.0_271-b09), built on Sep 16 2020 19:14:59 by "" with MS VC++ 15.9 (VS2017)
     * Memory: 4k page, physical 33449164k(22482756k free), swap 35546316k(19374956k free)
     * CommandLine flags: -XX:G1LogLevel=finest -XX:InitialHeapSize=134217728 -XX:MaxGCPauseMillis=20 -XX:MaxHeapSize=134217728 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTLAB -XX:+UnlockExperimentalVMOptions -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation
     * 3.964: [GC pause (G1 Evacuation Pause) (young)TLAB: gc thread: 0x0000020ffa41f000 [id: 17576] desired_size: 122KB slow allocs: 0  refill waste: 1960B alloc: 0.99996     6144KB refills: 1 waste 73.9% gc: 93040B slow: 0B fast: 0B
     * TLAB: gc thread: 0x0000020ffa3b5800 [id: 23056] desired_size: 122KB slow allocs: 0  refill waste: 1960B alloc: 0.99996     6144KB refills: 1 waste 99.6% gc: 125336B slow: 0B fast: 0B
     *
     * -- gc thread: 哪个线程 TLAB
     * -- desired_size: 122KB, 期待分配的 TLAB 的大小, TLABSize = Eden * 2 * 1% / 线程个数
     * -- slow allocs: 0, 为减慢分配的次数, 0 说明每次都是用了 TLAB 快速分配, 没有直接使用堆内存分配
     * -- refill waste: 1960B, 代表可以浪费的内存, 也是重新申请一个 TLAB 的值
     * -- alloc: 0.99996, 表示当前这个线程在一个分区中分配对象占用的比例, region 使用的比例
     * -- refills: 1, 出现了多少次飞起 TLAB(即填充一个 dummy 对象), 重新申请一个 TLAB 的次数, 次数为 1, 这个地方只是代表 refills 的次数也就是填充 TLAB 次数, 也可以理解为申请新的 TLAB 的次数
     * -- waste: 73.9%, 代表的是浪费的空间, 这个浪费的空间分为三个层面
     *  -- gc: 93040B, GC 时还没有使用的 TLAB 的空间. 现再正处于 GC 的 状态之中, TLAB 还剩下多少空间没有使用
     *  -- slow: 0B, 申请新的 TLAB 时, 旧的 TLAB 浪费的空间
     *  -- fast: 0B, 在出现需要调整 TLAB 的大小的时候, 即 refill_waste 不合理的时候, 旧的 TLAB 浪费的空间, dummy 对象造成的浪费
     *
     * TLAB: gc thread: 0x0000020fe4a7b000 [id: 24600] desired_size: 122KB slow allocs: 6  refill waste: 1960B alloc: 0.99996     6144KB refills: 48 waste  0.0% gc: 0B slow: 2064B fast: 0B
     * TLAB totals: thrds: 3  refills: 50 max: 48 slow allocs: 6 max 6 waste:  3.5% gc: 218376B max: 125336B slow: 2064B max: 2064B fast: 0B max: 0B
     * , 0.0030925 secs]
     *    [Parallel Time: 1.7 ms, GC Workers: 13]
     *       [GC Worker Start (ms):  3963.7  3963.8  3963.9  3963.9  3963.9  3964.0  3964.1  3964.1  3964.1  3964.1  3964.1  3964.1  3964.1
     *        Min: 3963.7, Avg: 3964.0, Max: 3964.1, Diff: 0.3]
     *       [Ext Root Scanning (ms):  0.2  0.2  0.1  0.1  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
     *          [Thread Roots (ms):  0.0  0.1  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
     *          [StringTable Roots (ms):  0.1  0.1  0.1  0.1  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.4]
     *          [Universe Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [JNI Handles Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [ObjectSynchronizer Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [FlatProfiler Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Management Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [SystemDictionary Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [CLDG Roots (ms):  0.1  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
     *          [JVMTI Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [CodeCache Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [CM RefProcessor Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Wait For Strong CLD (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Weak CLD Roots (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [SATB Filtering (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Update RS (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Processed Buffers:  0  0  0  0  0  0  0  0  0  0  0  0  0
     *           Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
     *       [Scan RS (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Code Root Scanning (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Object Copy (ms):  0.8  0.8  0.8  0.8  0.8  0.7  0.7  0.7  0.7  0.7  0.7  0.7  0.7
     *        Min: 0.7, Avg: 0.7, Max: 0.8, Diff: 0.1, Sum: 9.2]
     *       [Termination (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
     *          [Termination Attempts:  18  19  25  22  21  17  21  17  24  22  18  18  23
     *           Min: 17, Avg: 20.4, Max: 25, Diff: 8, Sum: 265]
     *       [GC Worker Other (ms):  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *        Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.3]
     *       [GC Worker Total (ms):  1.0  1.0  0.9  0.9  0.8  0.8  0.7  0.7  0.7  0.7  0.7  0.7  0.7
     *        Min: 0.7, Avg: 0.8, Max: 1.0, Diff: 0.3, Sum: 10.3]
     *       [GC Worker End (ms):  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8  3964.8
     *        Min: 3964.8, Avg: 3964.8, Max: 3964.8, Diff: 0.0]
     *    [Code Root Fixup: 0.0 ms]
     *    [Code Root Purge: 0.0 ms]
     *    [Clear CT: 0.5 ms]
     *    [Other: 0.9 ms]
     *       [Choose CSet: 0.0 ms]
     *       [Ref Proc: 0.7 ms]
     *       [Ref Enq: 0.0 ms]
     *       [Redirty Cards: 0.1 ms]
     *          [Parallel Redirty:  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0
     *           Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Redirtied Cards:  14  0  0  0  0  0  0  0  0  0  0  0  0
     *           Min: 0, Avg: 1.1, Max: 14, Diff: 14, Sum: 14]
     *       [Humongous Register: 0.0 ms]
     *          [Humongous Total: 0]
     *          [Humongous Candidate: 0]
     *       [Humongous Reclaim: 0.0 ms]
     *          [Humongous Reclaimed: 0]
     *       [Free CSet: 0.0 ms]
     *          [Young Free CSet: 0.0 ms]
     *          [Non-Young Free CSet: 0.0 ms]
     *    [Eden: 6144.0K(6144.0K)->0.0B(5120.0K) Survivors: 0.0B->1024.0K Heap: 6144.0K(128.0M)->2784.0K(128.0M)]
     *  [Times: user=0.00 sys=0.00, real=0.01 secs]
     */

}
