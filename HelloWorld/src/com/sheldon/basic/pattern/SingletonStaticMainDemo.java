package com.sheldon.basic.pattern;

public class SingletonStaticMainDemo {
	
	//�� new ��һ�� static class ��ʱ��, ��� static class �ͻ����ڴ���һֱ����.
	
	public static void main(String[] args) {
		
		SingletonStaticDemo singleton = SingletonStaticDemo.getSingleton();
		
		singleton.i = 20;
		
		SingletonStaticDemo singleton2 = SingletonStaticDemo.getSingleton();

	}
	
}
