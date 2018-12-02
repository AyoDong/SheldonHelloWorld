package com.sheldon.basic.pattern;

public class SingletonStaticDemo {
	
	public static int i = 0;

	private static SingletonStaticDemo singleton = null;
	
	public static SingletonStaticDemo getSingleton() {
		i ++;
		System.out.println("i : " + i);
		return singleton;
	}
}
