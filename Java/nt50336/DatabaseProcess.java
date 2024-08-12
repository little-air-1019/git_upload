package com.cathaybk.practice.nt50336;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DatabaseProcess {

	private static final String CONNECTION_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	private static final String USER_NAME = "student";
	private static final String PASSWORD = "student123456";

	public static void main(String[] args) {
		// Q7-(2)
//		 getAllCars();

		System.out.println("請選擇以下指令輸入：select、insert、update、delete\n");
		Scanner scanner = new Scanner(System.in);
		String command = scanner.next();
		switch (command) {
		case "select": {
			System.out.print("請輸入製造商：");
			String manufacturer = scanner.next();
			System.out.print("請輸入類別：");
			String type = scanner.next();
			query(manufacturer, type);
			break;
		}
		case "insert": {
			Map<String, String> insertMap = new HashMap<>();
			System.out.print("請輸入製造商：");
			insertMap.put("MANUFACTURER", scanner.next());
			System.out.print("請輸入類別：");
			insertMap.put("TYPE", scanner.next());
			if (query(insertMap.get("MANUFACTURER"), insertMap.get("TYPE")) == true) {
				System.out.println("資料已存在");
				break;
			}
			System.out.print("請輸入底價：");
			insertMap.put("MIN_PRICE", scanner.next());
			System.out.print("請輸入售價：");
			insertMap.put("PRICE", scanner.next());
			insert(insertMap);
			break;
		}

		case "update": {
			Map<String, String> updateMap = new HashMap<>();
			System.out.print("請輸入製造商：");
			updateMap.put("MANUFACTURER", scanner.next());
			System.out.print("請輸入類別：");
			updateMap.put("TYPE", scanner.next());
			if (query(updateMap.get("MANUFACTURER"), updateMap.get("TYPE")) == true) {
				System.out.println("資料不存在，請先新增");
				break;
			}
			System.out.print("請輸入底價：");
			updateMap.put("MIN_PRICE", scanner.next());
			System.out.print("請輸入售價：");
			updateMap.put("PRICE", scanner.next());
			update(updateMap);
			break;
		}

		case "delete": {
			System.out.print("請輸入製造商：");
			String manufacturer = scanner.next();
			System.out.print("請輸入類別：");
			String type = scanner.next();
			if (query(manufacturer, type) == false) {
				System.out.println("資料不存在，無法刪除");
				break;
			}
			delete(manufacturer, type);
			break;
		}

		default:
			System.out.println("指令輸入錯誤");
			break;
		}

		scanner.close();
	}

	private static void getAllCars() {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
				PreparedStatement pstmt = connection.prepareStatement("select * from STUDENT.CARS");
				ResultSet resultSet = pstmt.executeQuery()) {

			List<Map<String, String>> carsList = new ArrayList<>();

			while (resultSet.next()) {
				Map<String, String> car = new HashMap<>();
				car.put("MANUFACTURER", resultSet.getString("MANUFACTURER"));
				car.put("TYPE", resultSet.getString("TYPE"));
				car.put("PRICE", resultSet.getString("PRICE"));
				car.put("MIN_PRICE", resultSet.getString("MIN_PRICE"));
				carsList.add(car);
			}

			printCarsList(carsList);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	private static boolean query(String manufacturer, String type) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
				PreparedStatement pstmt = connection
						.prepareStatement("select * from STUDENT.CARS where MANUFACTURER = ? and TYPE = ?")) {

			pstmt.setString(1, manufacturer);
			pstmt.setString(2, type);

			try (ResultSet resultSet = pstmt.executeQuery()) {
				List<Map<String, String>> carsList = new ArrayList<>();

				while (resultSet.next()) {
					Map<String, String> car = new HashMap<>();
					car.put("MANUFACTURER", resultSet.getString("MANUFACTURER"));
					car.put("TYPE", resultSet.getString("TYPE"));
					car.put("PRICE", resultSet.getString("PRICE"));
					car.put("MIN_PRICE", resultSet.getString("MIN_PRICE"));
					carsList.add(car);
				}

				if (carsList.isEmpty()) {
					return false;
				} else {
					printCarsList(carsList);
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void printCarsList(List<Map<String, String>> carsList) {
		if (carsList.isEmpty()) {
			System.out.println("查無結果");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (Map<String, String> car : carsList) {
			sb.append("製造商：").append(car.get("MANUFACTURER")).append("，型號：").append(car.get("TYPE")).append("，售價：")
					.append(car.get("PRICE")).append("，底價：").append(car.get("MIN_PRICE"));
			System.out.println(sb.toString());
			sb.setLength(0);
		}
	}

	private static void insert(Map<String, String> insertMap) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
				PreparedStatement pstmt = connection.prepareStatement(
						"insert into STUDENT.CARS (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)")) {

			pstmt.setString(1, insertMap.get("MANUFACTURER"));
			pstmt.setString(2, insertMap.get("TYPE"));
			pstmt.setString(3, insertMap.get("MIN_PRICE"));
			pstmt.setString(4, insertMap.get("PRICE"));

			pstmt.executeUpdate();

			System.out.println("新增成功");

		} catch (SQLException e) {
			System.out.println("新增失敗，原因：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void update(Map<String, String> updateMap) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
				PreparedStatement pstmt = connection.prepareStatement(
						"update STUDENT.CARS set MIN_PRICE = ?, PRICE = ? where MANUFACTURER = ? and TYPE = ?")) {

			connection.setAutoCommit(false);

			pstmt.setString(1, updateMap.get("MIN_PRICE"));
			pstmt.setString(2, updateMap.get("PRICE"));
			pstmt.setString(3, updateMap.get("MANUFACTURER"));
			pstmt.setString(4, updateMap.get("TYPE"));

			pstmt.executeUpdate();

			connection.commit();
			System.out.println("更新成功");

		} catch (SQLException e) {
			System.out.println("更新失敗，原因：" + e.getMessage());
			e.printStackTrace();

			try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD)) {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
				System.out.println("rollback 失敗，原因：" + rollbackException.getMessage());
			}
		}
	}

	private static void delete(String manufacturer, String type) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
				PreparedStatement pstmt = connection
						.prepareStatement("delete from STUDENT.CARS where MANUFACTURER = ? and TYPE = ?")) {
			connection.setAutoCommit(false);

			pstmt.setString(1, manufacturer);
			pstmt.setString(2, type);
			pstmt.executeUpdate();

			connection.commit();
			System.out.println("刪除成功");
		} catch (SQLException e) {
			System.out.println("刪除失敗，原因：" + e.getMessage());
			e.printStackTrace();
			try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD)) {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
				System.out.println("rollback 失敗，原因：" + rollbackException.getMessage());
			}
		}
	}

}