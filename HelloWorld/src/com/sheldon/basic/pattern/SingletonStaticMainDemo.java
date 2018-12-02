package com.sheldon.basic.pattern;

public class SingletonStaticMainDemo {
	
	//当 new 了一个 static class 的时候, 则该 static class 就会在内存中一直存在.
	
	public static void main(String[] args) {
		
		SingletonStaticDemo singleton = SingletonStaticDemo.getSingleton();
		
		singleton.i = 20;
		
		SingletonStaticDemo singleton2 = SingletonStaticDemo.getSingleton();

	}
	
}
