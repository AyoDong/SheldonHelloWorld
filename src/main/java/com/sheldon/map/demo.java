package com.sheldon.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fangxiaodong
 * @date 2021/12/31
 */
public class demo {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> set1 = new HashSet<>();
        set1.add(1);

        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);

        List<Integer> l1 = new ArrayList<>();
        l1.add(1);
        l1.add(2);

        System.out.println(set.retainAll(set1));
        System.out.println(set1.retainAll(set));

        System.out.println(l.retainAll(l1));
        System.out.println(l1.retainAll(l));

        System.out.println(!Collections.disjoint(set, set1));
    }
}
