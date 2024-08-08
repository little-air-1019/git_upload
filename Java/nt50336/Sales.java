package com.cathaybk.practice.nt50336;

public class Sales extends Employee {
	private int bonus;
	private int payment;

	public Sales(String name, String department, int salary, int performance) {
		super(name, department, salary);
		this.bonus = (int) ((double) performance * 0.05);
		this.payment = salary + bonus;

	}

	@Override
	public void printInfo() {
		super.printInfo();
		System.out.printf("業績獎金： %d\n", bonus);
		System.out.printf("總計： %d\n", payment);

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
