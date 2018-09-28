package com.sheldon.basic.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IteratorPatternBeforeDemo {

	class IntegerBox {
		private final List<Integer> list = new ArrayList<>();

		public void add(int in) {
			list.add(in);
		}

		public List getData() {
			return list;
		}
	}

	public static void main(String[] args) {
		IteratorPatternBeforeDemo iteratorPatternBeforeDemo = new IteratorPatternBeforeDemo();
		IntegerBox box = iteratorPatternBeforeDemo.new IntegerBox();

		for (int i = 9; i > 0; --i) {
			box.add(i);
		}

		Collection integerList = box.getData();
		for (Object anIntegerList : integerList) {
			System.out.print(anIntegerList + "  ");
		}

		System.out.println();
		integerList.clear();
		integerList = box.getData();

		System.out.println("size of data is: " + integerList.size());
	}
}
