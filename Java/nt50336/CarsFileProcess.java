package com.cathaybk.practice.nt50336;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarsFileProcess {

	public static void main(String[] args) {
		try (Scanner intake = new Scanner(
				new File("C:\\Users\\Admin\\Desktop\\Java班\\git_upload\\Java\\Java評量_第6題cars.csv"));) {
			List<Map<String, String>> Cars = new ArrayList<>();
			intake.useDelimiter("\n");
			while (intake.hasNext()) {
				String[] aCar = (intake.next()).split(",");
				Map<String, String> map = new HashMap<>();
				map.put("Manufacturer", aCar[0]);
				map.put("Type", aCar[1]);
				map.put("Min.Price", aCar[2]);
				map.put("Price", aCar[3].trim()); // 確保字串內容可轉換為數值
				Cars.add(map);
			}

			if (!Cars.isEmpty()) {
				Cars.remove(0);
			}

			Cars.sort(new Comparator<Map<String, String>>() {
				@Override
				public int compare(Map<String, String> m1, Map<String, String> m2) {
					BigDecimal price1 = parsePrice(m1.get("Price"));
					BigDecimal price2 = parsePrice(m2.get("Price"));
					return -(price1.compareTo(price2));
				}

			});
			
			StringBuilder sb = new StringBuilder();
			try (BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("C:\\Users\\Admin\\Desktop\\Java班\\git_upload\\Java\\cars2.csv"), "UTF-8"))) {
				bWriter.write("\ufeff");
				bWriter.write("Manufacturer,Type,Min.Price,Price\n");
								
				for (Map<String, String> car : Cars) {
					sb.append(car.get("Manufacturer")).append(",").append(car.get("Type")).append(",")
							.append(car.get("Min.Price")).append(",").append(car.get("Price")).append("\n");
					bWriter.write(sb.toString());
					sb.setLength(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Map<String, List<Map<String, String>>> carsGroupMap = Cars.stream()
					.collect(Collectors.groupingBy(car -> car.get("Manufacturer")));

			System.out.println("Manufacturer TYPE    Min.PRICE Price");
			BigDecimal totalMinPrice = BigDecimal.ZERO;
			BigDecimal totalPrice = BigDecimal.ZERO;
			
			for (Map.Entry<String, List<Map<String, String>>> carsGroup : carsGroupMap.entrySet()) {
				List<Map<String, String>> carList = carsGroup.getValue();
				BigDecimal sumMinPrice = BigDecimal.ZERO;
				BigDecimal sumPrice = BigDecimal.ZERO;

				for (Map<String, String> car : carList) {
					sumMinPrice = sumMinPrice.add(parsePrice(car.get("Min.Price")));
					sumPrice = sumPrice.add(parsePrice(car.get("Price")));
					sb.append(String.format("%-13s", car.get("Manufacturer")))
							.append(String.format("%-10s", car.get("Type")))
							.append(String.format("%-8s", car.get("Min.Price"))).append(car.get("Price"));
					System.out.println(sb.toString());
					sb.setLength(0);
				}
				sb.append(String.format("%-22s", "小計")).append(String.format("%-8s", sumMinPrice.toPlainString()))
						.append(sumPrice.toPlainString());
				System.out.println(sb.toString());
				sb.setLength(0);

				totalMinPrice = totalMinPrice.add(sumMinPrice);
				totalPrice = totalPrice.add(sumPrice);
			}
			sb.append(String.format("%-22s", "合計")).append(String.format("%-8s", totalMinPrice.toPlainString())) // 不用 toString 原因(科學符號有加減等)
					.append(totalPrice.toPlainString());
			System.out.println(sb.toString());
			sb.setLength(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

	}

	private static BigDecimal parsePrice(String priceString) {
		try {
			return new BigDecimal(priceString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}

}
