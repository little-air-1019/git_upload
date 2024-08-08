package com.cathaybk.practice.nt50336;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLotto {

	public static void main(String[] args) {
		List<Integer> numList = new ArrayList<>();
		Random random = new Random();
		System.out.print("排序前：");
		while (numList.size() < 6) {
			int num = random.nextInt(49) + 1;
			if (!numList.contains(num)) {
				numList.add(num);
				System.out.print(num + " ");
			}
		}
		System.out.println();
		System.out.print("排序後：");
		numList.sort(null);
		for (int num : numList) {
			System.out.print(num + " ");
		}
	}
}
