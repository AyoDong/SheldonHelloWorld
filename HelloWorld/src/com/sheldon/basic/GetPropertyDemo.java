package com.sheldon.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPropertyDemo {

	
	public static void main(String[] args) {
		
		Map<String, List<String>> maps = new HashMap<>();
		
		maps.put("A", new ArrayList<>());
		maps.put("B", new ArrayList<>());
		maps.put("C", new ArrayList<>());
		maps.put("D", new ArrayList<>());
		
		maps.get("A").add("A1");
		maps.get("A").add("A2");
		maps.get("A").add("A3");
		maps.get("B").add("A3");
		maps.get("C").add("A3");
		maps.get("D").add("A3");
		
		List<String> lists = new ArrayList<>(maps.keySet());
		System.out.println(lists);
		for(String str : lists) {
			System.out.println(str);
		}
		
		for(String key : maps.keySet()) {
			for(String s : maps.get(key)) {
				System.out.println(s);
			}
		}
		
	}
}
