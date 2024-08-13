package com.cathaybk.practice.nt50336;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Scanner;

public class Calendar {

	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.print("輸入介於1-12間的整數m:");
			int month = input.nextInt();
			Boolean ifMonthInRange = month >=1 && month <= 12;
			while (ifMonthInRange == false) {
				System.out.println("月份輸入錯誤，請重新輸入:");
				month = input.nextInt();
				ifMonthInRange = month >=1 && month <= 12;
			}
			display(month);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void display(int month) {
		int year = Year.now().getValue();
		System.out.printf("      %4s年%d月       \n", year, month);
		System.out.println("---------------------");
		System.out.println("日  一  二  三  四  五  六 ");
		System.out.println("=====================");
		int day = LocalDate.of(2024, month, 1).getDayOfWeek().getValue();

		int count = 1;
		if (day != 6) {
			for (int i = 0; i < day; i++, count++) {
				System.out.printf("%-3s", "");
			}
		}

		YearMonth yearMonth = YearMonth.of(year, month);
		for (int i = 1; i <= yearMonth.getMonth().length(yearMonth.isLeapYear()); i++, count++) {
			System.out.printf("%3d", i);
			if ((count % 7 == 0) || (i == yearMonth.getMonth().length(yearMonth.isLeapYear()))) {
				System.out.println();
			}
		}
		System.out.println("---------------------");
	}

}
