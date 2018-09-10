package com.sheldon.basic;

import java.io.File;
import java.io.IOException;

public class IODemo {

	public static void main(String[] args) throws IOException {
		/*
		String fileName = "E://testNote.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		int position = 0;
		while(br.ready()) {
			position = br.readLine().indexOf("FUCK");
		}
		br.close();
		
		String fileName = "E://testNote.txt";
		FileWriter fw = new FileWriter(new File(fileName));
		
		fw.write("HelloWorld_1 \r\n");
		fw.write("HelloWorld_2 \r\n");
		
		fw.close();
		*/
		
		String writePath = "E://test.txt";
		File file = new File(writePath);
		if(!file.exists()) {
			File fatherFile = file.getParentFile();
			System.out.println(fatherFile.getName());
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
	}
}
