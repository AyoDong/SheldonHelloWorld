package com.sheldon.basic;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

	public static void main(String[] args) {
		Map<String, String> maps = new HashMap<>();
		maps.put("A", "A");
		maps.put("B", "B");
		maps.put("C", "C");
		maps.put("D", "D");
		
		maps.remove("A");
		
		for(String key : maps.keySet()) {
			System.out.println(key);
			System.out.println(maps.get(key));
		}
	}
	
}
