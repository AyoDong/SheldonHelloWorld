package com.sheldon.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloIODemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		char c = ' ';
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(c!='q') {
			c = (char) br.read();
			System.out.println(c);
		}
	}

}
