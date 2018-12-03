package com.sheldon.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TwoBrothersDigForTruesure {

	public static void main(String[] args) {
		List<Integer> digOutResult = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		Map<Integer, Integer> personQuality = new HashMap<>();
		List<Integer> truesurePosition = new ArrayList<>();
		personQuality.put(1, sc.nextInt());
		personQuality.put(-1, sc.nextInt());

		int quantity = sc.nextInt();
		for (int i = 0; i < quantity; i++) {
			truesurePosition.add(sc.nextInt());
		}

		int maxTruesurePostion = getMaxTruesurePosition(truesurePosition);

		getDigOutPersonList(personQuality, 1, 0, maxTruesurePostion, truesurePosition, digOutResult);

		for (int i = 0, size = digOutResult.size(); i < size; i++) {
			if(i==0) {
				System.out.print(digOutResult.get(i));
			}else {
				System.out.print(" " + digOutResult.get(i));
			}
		}
	}

	public static void getDigOutPersonList(Map<Integer, Integer> personQuality, int firstPerson, int totalDepth,
			int maxTruesurePostion, List<Integer> truesurePosition, List<Integer> digOutResult) {

		if (totalDepth >= maxTruesurePostion) {
			return;
		}

		totalDepth += personQuality.get(firstPerson);

		if (truesurePosition.contains(totalDepth)) {
			digOutResult.add(firstPerson);
			firstPerson *= -1;
			getDigOutPersonList(personQuality, firstPerson, totalDepth, maxTruesurePostion, truesurePosition,
					digOutResult);
		} else {
			firstPerson *= -1;
			getDigOutPersonList(personQuality, firstPerson, totalDepth, maxTruesurePostion, truesurePosition,
					digOutResult);
		}

	}

	public static Integer getMaxTruesurePosition(List<Integer> truesurePosition) {
		int temp = 0;
		for (Integer truesure : truesurePosition) {
			if (truesure > 0) {
				temp = truesure;
			}
		}
		return temp;
	}

}
