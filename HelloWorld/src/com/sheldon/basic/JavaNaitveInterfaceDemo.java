package com.sheldon.basic;

public class JavaNaitveInterfaceDemo {

	static {
		System.loadLibrary("HelloNayive");
	}
	
	public static native void sayHello();
	
	public static void main(String[] args) {
		new JavaNaitveInterfaceDemo().sayHello();
	}
	
}
