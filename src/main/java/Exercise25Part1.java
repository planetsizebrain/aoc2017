import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Exercise25Part1 {

	public static void main(String[] args) throws IOException {
		char state = 'A';
		int steps = 12481997;
		int position = 0;
		Map<Integer, Integer> memory = new HashMap<>();
		for (int i = 0; i < steps; i++) {
			Integer value = memory.get(position);
			if (value == null) {
				value = 0;
			}

			switch (state) {
				case 'A': {
					if (value == 0) {
						memory.put(position++, 1);
						state = 'B';
					} else {
						memory.put(position--, 0);
						state = 'C';
					}

					break;
				}
				case 'B': {
					if (value == 0) {
						memory.put(position--, 1);
						state = 'A';
					} else {
						memory.put(position++, 1);
						state = 'D';
					}

					break;
				}
				case 'C': {
					if (value == 0) {
						memory.put(position--, 0);
						state = 'B';
					} else {
						memory.put(position--, 0);
						state = 'E';
					}

					break;
				}
				case 'D': {
					if (value == 0) {
						memory.put(position++, 1);
						state = 'A';
					} else {
						memory.put(position++, 0);
						state = 'B';
					}

					break;
				}
				case 'E': {
					if (value == 0) {
						memory.put(position--, 1);
						state = 'F';
					} else {
						memory.put(position--, 1);
						state = 'C';
					}

					break;
				}
				case 'F': {
					if (value == 0) {
						memory.put(position++, 1);
						state = 'D';
					} else {
						memory.put(position++, 1);
						state = 'A';
					}

					break;
				}
			}
		}

		System.out.println(memory.values().stream().filter(v -> v == 1).count());
	}
}