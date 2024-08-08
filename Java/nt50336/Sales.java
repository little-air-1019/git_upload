package com.cathaybk.practice.nt50336;

import java.math.BigDecimal;

public class Sales extends Employee {
	private int bonus;
	private int payment;

	public Sales(String name, String department, int salary, int performance) {
		super(name, department, salary);
		this.bonus = calculateBonus(performance);
		this.payment = calculatePayment();

	}

	@Override
	public void printInfo() {
		super.printInfo();
		System.out.printf("業績獎金： %d\n", bonus);
		System.out.printf("總計： %d\n", payment);

	}

	private int calculateBonus(int performanceInt) {
		return new BigDecimal(performanceInt).multiply(new BigDecimal(0.05)).intValue();
	}

	private int calculatePayment() {
		return new BigDecimal(this.getSalary()).add(new BigDecimal(this.bonus)).intValue();
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

}
