package com.sheldon.basic;

public class VariablesInitDemo {
	
	private String str;
	
	private static String staStr;
	
	// Final must be initialized
	private final String fiStr = "fiStr";
	
	public static void main(String[] args) {
	
		//local variables have to be initialized
		String loStr = null;
		final String loStaStr = null;
		
		VariablesInitDemo v = new VariablesInitDemo();
		
		System.out.println(v.str);
		System.out.println(staStr);
		System.out.println(v.fiStr);
		
		System.out.println(loStr);
		System.out.println(loStaStr);
		
	}
	
}
