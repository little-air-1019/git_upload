package com.cathaybk.practice.nt50336;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class HRMain {

	public static void main(String[] args) {
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Sales("張志城", "信用卡部", 35000, 6000));
		employeeList.add(new Sales("林大鈞", "保代部", 38000, 4000));
		employeeList.add(new Supervisor("李中白", "資訊部", 65000));
		employeeList.add(new Supervisor("林小白", "理財部", 80000));
		for (Employee employee : employeeList) {
			employee.printInfo();
		}

		// 第四題
		try (BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Admin\\Desktop\\Java班\\Java\\output.csv"), "UTF-8"))) {
			bWriter.write("\ufeff");
			for (Employee employee : employeeList) {
				bWriter.write(employee.getName() + "," + employee.getPayment() + "\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}