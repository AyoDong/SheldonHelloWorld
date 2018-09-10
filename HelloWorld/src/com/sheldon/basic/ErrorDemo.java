package com.sheldon.basic;

public class ErrorDemo {

	public static void main(String[] args) {
		Error e = new Error("error");
		System.out.println(e.getStackTrace());
		e.printStackTrace();
	}
	
}
