import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.primitives.Longs;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise23Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise23.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Map<String, Long> registers = new HashMap<>();

		int position = 0;
		long multiplications = 0;
		while (position >= 0 && position < inputs.size()) {
			String[] input = inputs.get(position).split(" ");

			String operation = input[0];
			String register = input[1];
			long value = getValue(input[2], registers);

			System.out.println(position + " - " + inputs.get(position));

			switch (operation) {
				case "set": {
					registers.put(register, value);
					break;
				}
				case "sub": {
					registers.put(register, getRegisterValue(register, registers) - value);
					break;
				}
				case "mul": {
					multiplications++;
					registers.put(register, getRegisterValue(register, registers) * value);
					break;
				}
			}

			if (operation.equals("jnz")) {
				long jump = getValue(register, registers);
				if (jump != 0) {
					position += value;
				} else {
					position++;
				}
			} else {
				position++;
			}

			registers.entrySet().stream().forEach(e -> System.out.print(e.getKey() + " -> " + e.getValue() + ", "));
			System.out.println();
		}

		System.out.println(multiplications);
	}

	private static long getRegisterValue(String register, Map<String, Long> registers) {
		if (registers.containsKey(register)) {
			return registers.get(register);
		}

		return 0;
	}

	private static long getValue(String value, Map<String, Long> registers) {
		Long number = Longs.tryParse(value);

		if (number == null) {
			number = getRegisterValue(value, registers);
		}

		return number;
	}
}