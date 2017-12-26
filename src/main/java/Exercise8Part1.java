import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise8Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise8.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		Map<String, Integer> registers = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" ");
			registers.put(parts[0], 0);
		}

		for (String line : lines) {
			String[] parts = line.split(" ");

			String register = parts[0];
			String valueOperator = parts[1];
			int value = Integer.parseInt(parts[2]);
			String checkRegister = parts[4];
			String operator = parts[5];
			int checkValue = Integer.parseInt(parts[6]);

			boolean valid = false;
			int registerValue = registers.get(checkRegister);
			switch (operator) {
				case "<": {
					valid = registerValue < checkValue;
					break;
				}
				case "<=": {
					valid = registerValue <= checkValue;
					break;
				}
				case "==": {
					valid = registerValue == checkValue;
					break;
				}
				case "!=": {
					valid = registerValue != checkValue;
					break;
				}
				case ">": {
					valid = registerValue > checkValue;
					break;
				}
				case ">=": {
					valid = registerValue >= checkValue;
					break;
				}
			}

			if (valid) {
				int currentValue = registers.get(register);

				switch (valueOperator) {
					case "inc": {
						registers.put(register, currentValue + value);
						break;
					}
					case "dec": {
						registers.put(register, currentValue - value);
						break;
					}
				}
			}
		}

		System.out.println(registers.values().stream().max(Integer::compareTo).get());
	}
}