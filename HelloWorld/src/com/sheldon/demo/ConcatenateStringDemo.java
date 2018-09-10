package com.sheldon.demo;

public class ConcatenateStringDemo {
	public static char[] text = { 'a', 'b', 'c', 'd', 'e' };

	public static void main(String[] args) {
		permutation(text, 0, text.length);
		System.exit(0);
	}

	/**
	 * ȫ�������
	 * 
	 * @param   a[] Ҫ������ַ�����
	 * @param m ����ַ��������ʼλ��
	 * @param n ����ַ�����ĳ���
	 */
	public static void permutation(char a[], int m, int n) {
		int i;
		char t;
		if (m < n - 1) {
			permutation(a, m + 1, n);
			for (i = m + 1; i < n; i++) {
				t = a[m];
				a[m] = a[i];
				a[i] = t;
				permutation(a, m + 1, n);
				t = a[m];
				a[m] = a[i];
				a[i] = t;
			}
		} else {
			printResult(a);
		}
	}

	/**
	 * ���ָ���ַ�����
	 * 
	 * @param text ��Ҫ������ַ�����
	 */
	public static void printResult(char[] text) {
		for (int i = 0; i < text.length; i++) {
			System.out.print(text[i]);
		}
		System.out.println();
	}
}
