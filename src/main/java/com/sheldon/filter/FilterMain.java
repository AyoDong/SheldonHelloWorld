package com.sheldon.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangxiaodong
 * @date 2020/12/29
 */
public class FilterMain {

    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        strs.add("1");
        strs.add("2");
        strs.add("3");

        List<String> filterStrs = strs.stream().filter(e->!e.equals("1")).collect(Collectors.toList());
        System.out.println(filterStrs);
        System.out.println(strs);

    }

}
