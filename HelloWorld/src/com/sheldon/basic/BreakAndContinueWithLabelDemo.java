package com.sheldon.basic;

public class BreakAndContinueWithLabelDemo {

	public static void main(String[] args) {
		label_1: for (int i = 0; i < 10; i++) {
			label_2: for (int j = 0; j < 10; j++) {
				System.out.println("i = "+ i +" j = " + j);
				/*
				if(j % 2 == 0) {
					System.out.println("continue label_2");
					continue label_2;
				}
				
				if(j % 2 == 1) {
					System.out.println("continue label_1");
					continue label_1;
				}
				*/
				if(j % 2 == 0) {
					System.out.println("break label_1");
					break label_1;
				}
				
				if(j % 2 == 1) {
					System.out.println("break label_2");
					break label_2;
				}
			}
		}
	}
}
