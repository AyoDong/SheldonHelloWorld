package com.sheldon.basic;

public class NonStaticMugDemo {

	public NonStaticMugDemo() {
		System.out.println("Constructor");
	}
	
	static {
		System.out.println("It's NonStaticMugDemo");
	}
}
