package com.sheldon.async.service;

public class ExecutorClientDemo {

	public static void main(String[] args) {
		boolean r = task2();
		if(r) {
			task3();
		}
		
		System.out.println("---- main end ----");
	}
	
	static boolean task2() {
		ExecutorDemo executor = new ExecutorDemo();
		executor.asyncTask();
		System.out.println("---- task2 end ----");
		return true;
	}

	static void task3() {
		int j=0;
		while (true) {
			if(j++ > 10000) {
				break;
			}
		}
		System.out.println("---- task3 end ----");
	}
}
