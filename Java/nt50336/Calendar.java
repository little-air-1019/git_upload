package com.cathaybk.practice.nt50336;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class Calendar {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("輸入介於1-12間的整數m:");
		int month = input.nextInt();
		display(month);

	}
	
	public static void display(int month) {
		System.out.println("--------------------");
		System.out.println("日 一 二 三 四 五 六");
		System.out.println("====================");
		int day = LocalDate.of(2024, month, 1).getDayOfWeek().getValue();
		
		int count = 1;
		if (day != 6) {
			for (int i = 0; i < day; i++, count++) {
				System.out.printf("%-4s", "");
			}
		}
		
		YearMonth yearMonth = YearMonth.of(2024, month);
		for (int i = 1; i <= yearMonth.getMonth().length(yearMonth.isLeapYear()); i++, count++) {
			System.out.printf("%-4d", i);
			if (count % 7 == 0) {
				System.out.println();
			}
		}
		System.out.println("--------------------");
	}
		
}
