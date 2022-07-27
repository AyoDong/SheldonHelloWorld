package com.sheldon.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangxiaodong
 * @date 2021/05/27
 */
public class HashMapDemo {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(1);
        map.put("abc", "abc");
        map.put("abc", "fxxk");

    }

}
