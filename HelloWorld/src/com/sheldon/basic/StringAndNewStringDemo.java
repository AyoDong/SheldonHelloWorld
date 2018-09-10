package com.sheldon.basic;

public class StringAndNewStringDemo {

	public static void main(String[] args) {
	// TODO Auto-generated method stub
		
		String a = "a";
		String b = "a";
		String c = new String("a");
		
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		a = "b";
		
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	
}
