package com.sheldon.leetcode;

public class ValidSquare {

	public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
		return false;
	}

	public boolean checkIfSquare(int[][] squareArray) {
		return false;
	}

	public void swap(int[][] squareArray, int startIndex, int recursionIndex) {
		int[] temp = squareArray[startIndex];
		squareArray[startIndex] = squareArray[recursionIndex];
		squareArray[recursionIndex] = temp;
	}

	public boolean mainRecursionMethod(int[][] squareArray, int recursionIndex) {
		if (recursionIndex < squareArray.length) {
			return checkIfSquare(squareArray);
		} else {
			boolean flag = false;
			for (int i = recursionIndex; i < squareArray.length; i++) {
				swap(squareArray, i, recursionIndex);
				flag |= mainRecursionMethod(squareArray, recursionIndex++);
				swap(squareArray, i, recursionIndex);
			}
			return flag;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(1440 % 408);
	}
	
}
