import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.primitives.Longs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise18Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise18.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Map<Integer, Map<String, Long>> memory = new HashMap<>();
		HashMap<String, Long> m0 = new HashMap<>();
		m0.put("p", 0L);
		memory.put(0, m0);
		HashMap<String, Long> m1 = new HashMap<>();
		m1.put("p", 1L);
		memory.put(1, m1);

		Map<Integer, Integer> positions = new HashMap<>();
		positions.put(0, 0);
		positions.put(1, 0);

		Map<Integer, List<Long>> queues = new HashMap<>();
		queues.put(0, new ArrayList<>());
		queues.put(1, new ArrayList<>());

		int sends = 0;
		boolean deadlock = false;
		while (validPositions(positions, inputs.size()) && !deadlock) {
			for (int i = 0; i < 2; i++) {
				Map<String, Long> registers = memory.get(i);
				int position = positions.get(i);

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
						queues.get(i == 0 ? 1 : 0).add(getValue(register, registers));
						if (i == 1) {
							sends++;
						}

						break;
					}
					case "rcv": {
						if (inputs.get(positions.get(0)).startsWith("rcv") && inputs.get(positions.get(1)).startsWith("rcv") &&
								queues.get(0).size() == 0 && queues.get(1).size() == 0) {
							deadlock = true;
						}

						if (queues.get(i).size() > 0) {
							registers.put(register, queues.get(i).remove(0));
							position++;
						}

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
				} else if (!operation.equals("rcv")) {
					position++;
				}

				positions.put(i, position);
			}
		}

		System.out.println(sends);
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

	private static boolean validPositions(Map<Integer, Integer> positions, int length) {
		for (Integer position : positions.values()) {
			if (position < 0 || position >= length) {
				return false;
			}
		}

		return true;
	}
}