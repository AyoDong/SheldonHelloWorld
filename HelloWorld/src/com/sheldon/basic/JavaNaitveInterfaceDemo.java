package com.sheldon.basic;

public class JavaNaitveInterfaceDemo {

	//Haven't Done
	
	static {
		System.loadLibrary("HelloNayive");
	}
	
	public static native void sayHello();
	
	public static void main(String[] args) {
		new JavaNaitveInterfaceDemo().sayHello();
	}
	
}
