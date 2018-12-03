package com.sheldon.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeftBraceAndRightBraceCombination {

	public static List<String> generateParenthesis(int n) {
		List<String> ans = new ArrayList<>();
		if (n == 0) {
			ans.add("");
		} else {
			for (int c = 0; c < n; ++c)
				for (String left : generateParenthesis(c))
					for (String right : generateParenthesis(n - 1 - c))
						ans.add("(" + left + ")" + right);
		}
		return ans;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int m = scan.nextInt();
		int n = scan.nextInt();

		if (m > n) {
			System.out.println(generateParenthesis(n));
		} else {
			System.out.println(generateParenthesis(m));
		}

	}
}
