import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise18Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise18.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Map<String, Long> registers = new HashMap<>();

		int position = 0;
		long lastSound = 0;
		while (position >= 0 && position < inputs.size()) {
			String[] input = inputs.get(position).split(" ");

			String operation = input[0];
			String register = input[1];
			long value = 0;
			if (input.length > 2) {
				value = getValue(input[2], registers);
			}

			switch (operation) {
				case "set": {
					registers.put(register, value);
					break;
				}
				case "add": {
					registers.put(register, getRegisterValue(register, registers) + value);
					break;
				}
				case "mul": {
					registers.put(register, getRegisterValue(register, registers) * value);
					break;
				}
				case "mod": {
					registers.put(register, getRegisterValue(register, registers) % value);
					break;
				}
				case "snd": {
					lastSound = registers.get(register);
					System.out.println(register + " " + lastSound);
					break;
				}
			}

			if (operation.equals("jgz")) {
				long jump = getValue(register, registers);
				if (jump > 0) {
					position += value;
				} else {
					position++;
				}
			} else {
				position++;
			}

			if (operation.equals("rcv")&& registers.get(register) != 0) {
				System.out.println(lastSound);
				break;
			}
		}
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