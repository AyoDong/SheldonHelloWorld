package com.sheldon.jvm.gc.cms.demo;

/**
 * @author fangxiaodong
 * @date 2021/09/30
 */
public class Demo5 {

    /**
     * -XX:NewSize=104857600
     * -XX:MaxNewSize=104857600
     * -XX:InitialHeapSize=209715200
     * -XX:MaxHeapSize=209715200
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=3145728
     * -XX:+UseParNewGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -Xloggc:gc.log
     *
     * 新生代初始值和新生代的最大值是 100MB, Eden = 80MB, Survivor From = 10MB, Survivor To = 10MB
     * 堆内存初始值和堆内存最大值是 200MB
     * 大对象进入老年代的阈值: 20MB
     *
     * 一次性创建 4 * 10MB 的数组, 然后在创建 3 个有引用的 4 * 10MB 的数组
     * 在 data3 再次申请 10MB 的时候, 已经存在 4 * 10MB + 3 * 10MB = 70MB, 还有一些其他的未知对象, 此时已经不够空间了
     * 这个时候会触发 Minor GC, 这个时候, 就会把 3 个有引用的 30MB 存放在老年代里
     *
     * 然后再次循环的时候, 老年代的内存大概就是 70MB, 此时 data3 在创建的时候, 会看新生代全部内存是否可以完全接收, 大约比 70MB 还多
     * 因此不能接收, 就会进行一次 Full GC, 这个时候, 原本的 3 个没有引用的 30MB 会给回收走, 然后 Minor GC 之后又会放入 30MB + 其他对象
     *
     * 这里为什么 YGCT 会比较慢, 10 次的 Minor GC 就要使用 0.061s, 对应 5 次 Full GC 只用 0.013s
     * 原因是因为, Minor GC 是在 Full GC 之后的, 所以 Full GC 的时间 + Minor GC 的时候, 才是 YGCT 的时间
     *
     *  S0C     S1C     S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU     YGC     YGCT   FGC    FGCT     GCT
     * 10240.0 10240.0  0.0    0.0   81920.0   6553.7   102400.0     0.0     4480.0 776.5  384.0   76.6       0    0.000   0      0.000    0.000
     * 10240.0 10240.0  0.0   1051.2 81920.0  10240.0   102400.0   30722.1   4864.0 3801.7 512.0  419.4       1    0.016   0      0.000    0.016
     * 10240.0 10240.0 1515.7  0.0   81920.0  23631.3   102400.0   51204.2   4864.0 3802.1 512.0  419.4       2    0.027   1      0.010    0.037
     * 10240.0 10240.0  0.0   1592.9 81920.0  32252.7   102400.0   61446.2   4864.0 3802.2 512.0  419.4       3    0.032   1      0.010    0.042
     * 10240.0 10240.0 1335.5  0.0   81920.0  42517.7   102400.0   30726.2   4864.0 3802.2 512.0  419.4       4    0.033   2      0.011    0.044
     * 10240.0 10240.0  0.0   1535.8 81920.0  52773.8   102400.0   40966.2   4864.0 3802.2 512.0  419.4       5    0.035   2      0.011    0.046
     * 10240.0 10240.0 1659.5  0.0   81920.0  63024.1   102400.0   51206.2   4864.0 3802.2 512.0  419.4       6    0.038   2      0.011    0.048
     * 10240.0 10240.0  0.0   1398.1 81920.0  73270.8   102400.0   61446.2   4864.0 3802.2 512.0  419.4       7    0.040   3      0.011    0.051
     * 10240.0 10240.0  0.0   1596.8 81920.0  10240.0   102400.0   40968.2   4864.0 3802.2 512.0  419.4       9    0.057   4      0.013    0.070
     * 10240.0 10240.0 1525.5  0.0   81920.0  22079.4   102400.0   61448.3   4864.0 3802.2 512.0  419.4      10    0.061   5      0.013    0.074
     * 10240.0 10240.0  0.0   1523.6 81920.0  32320.6   102400.0   71688.3   4864.0 3802.2 512.0  419.4      11    0.063   5      0.013    0.076
     * 10240.0 10240.0 1435.5  0.0   81920.0  42561.3   102400.0   30728.2   4864.0 3802.2 512.0  419.4      12    0.064   6      0.014    0.078
     * 10240.0 10240.0  0.0   1508.4 81920.0  52801.8   102400.0   40968.2   4864.0 3802.2 512.0  419.4      13    0.066   6      0.014    0.080
     * 10240.0 10240.0 1589.6  0.0   81920.0  63042.1   102400.0   51208.3   4864.0 3802.2 512.0  419.4      14    0.068   6      0.014    0.083
     * 10240.0 10240.0  0.0   1523.6 81920.0  73282.3   102400.0   61448.3   4864.0 3802.2 512.0  419.4      15    0.070   7      0.014    0.085
     * 10240.0 10240.0  0.0    0.0   81920.0  10240.0   102400.0   42038.4   4864.0 3802.2 512.0  419.4      17    0.078   8      0.016    0.094
     * 10240.0 10240.0  0.0    0.0   81920.0  22082.5   102400.0   62518.4   4864.0 3802.2 512.0  419.4      18    0.082   9      0.016    0.098
     * 10240.0 10240.0  0.0    0.0   81920.0  32322.6   102400.0   72758.4   4864.0 3802.2 512.0  419.4      19    0.083   9      0.016    0.100
     * 10240.0 10240.0  0.0    0.0   81920.0  42562.6   102400.0   72758.4   4864.0 3802.2 512.0  419.4      20    0.084   9      0.016    0.100
     * 10240.0 10240.0  0.0    0.0   81920.0  52802.6   102400.0   42037.3   4864.0 3802.2 512.0  419.4      21    0.086  10      0.017    0.103
     * 10240.0 10240.0  0.0    0.0   81920.0  63042.6   102400.0   52277.4   4864.0 3802.2 512.0  419.4      22    0.088  10      0.017    0.105
     * 10240.0 10240.0  0.0    0.0   81920.0  73282.7   102400.0   62517.4   4864.0 3802.2 512.0  419.4      23    0.090  11      0.017    0.107
     * 10240.0 10240.0  0.0    0.0   81920.0  10240.0   102400.0   42037.3   4864.0 3802.2 512.0  419.4      25    0.095  12      0.019    0.114
     * 10240.0 10240.0  0.0    0.0   81920.0  22082.6   102400.0   62517.4   4864.0 3802.2 512.0  419.4      26    0.099  13      0.019    0.118
     *
     * 优化
     * -XX:NewSize=209715200
     * -XX:MaxNewSize=209715200
     * -XX:InitialHeapSize=314572800
     * -XX:MaxHeapSize=314572800
     * -XX:SurvivorRatio=2
     *
     * 这里增加内存的大小, 新生代 200MB, 堆内存 300MB, 此时设置 Survivor 区的内存也多点, Eden 和 survivor 2:1:1(Eden 100MB, Survivor1 50MB,
     * Survivor2 50MB)
     *
     * 这里 Eden 每次都能存放里面的内存, 此时每次 Minor GC 之后往 Survivor 存放数据也是维持在小于 Survivor 区 50%
     * 这个时候特别是不会触发 Full GC, 10 次 Minor GC 是用的时间也会快很多 0.051s
     *
     *  S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
     * 51200.0 51200.0  0.0    0.0   102400.0  8192.1   102400.0     0.0     4480.0 776.5  384.0   76.6       0    0.000   0      0.000    0.000
     * 51200.0 51200.0  0.0   11360.6 102400.0 73644.5   102400.0     0.0     4864.0 3798.0 512.0  419.4       1    0.006   0      0.000    0.006
     * 51200.0 51200.0 11599.7  0.0   102400.0 65400.0   102400.0     0.0     4864.0 3798.4 512.0  419.4       2    0.012   0      0.000    0.012
     * 51200.0 51200.0  0.0   11788.4 102400.0 53130.1   102400.0     0.0     4864.0 3798.5 512.0  419.4       3    0.014   0      0.000    0.014
     * 51200.0 51200.0 1283.4  0.0   102400.0 42916.7   102400.0     0.0     4864.0 3798.5 512.0  419.4       4    0.014   0      0.000    0.014
     * 51200.0 51200.0  0.0   11779.2 102400.0 32693.7   102400.0     0.0     4864.0 3798.5 512.0  419.4       5    0.017   0      0.000    0.017
     * 51200.0 51200.0 22195.9  0.0   102400.0 22464.7   102400.0     0.0     4864.0 3798.5 512.0  419.4       6    0.025   0      0.000    0.025
     * 51200.0 51200.0  0.0   32095.1 102400.0 10240.0   102400.0     0.0     4864.0 3798.5 512.0  419.4       7    0.043   0      0.000    0.043
     * 51200.0 51200.0  0.0   32095.1 102400.0 94151.9   102400.0     0.0     4864.0 3798.5 512.0  419.4       7    0.043   0      0.000    0.043
     * 51200.0 51200.0  0.0    0.0   102400.0 83916.4   102400.0    1078.3   4864.0 3798.5 512.0  419.4       8    0.047   0      0.000    0.047
     * 51200.0 51200.0  0.0   10240.0 102400.0 73679.3   102400.0    1078.3   4864.0 3798.5 512.0  419.4       9    0.049   0      0.000    0.049
     * 51200.0 51200.0 10240.0  0.0   102400.0 63441.1   102400.0    1078.3   4864.0 3798.5 512.0  419.4      10    0.051   0      0.000    0.051
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(30000);
        while(true){
            load();
        }
    }

    private static void load() throws InterruptedException {
        byte[] data = null;
        for (int i = 0; i < 4; i++){
            data = new byte[10 * 1024 * 1024];
        }
        data = null;

        byte[] data1 = new byte[10 * 1024 * 1024];
        byte[] data2 = new byte[10 * 1024 * 1024];
        byte[] data3 = new byte[10 * 1024 * 1024];
        data3 = new byte[10 * 1024 * 1024];

        Thread.sleep(1000);
    }
}
