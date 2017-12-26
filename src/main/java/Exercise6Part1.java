import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Exercise6Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise6.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

		int[] parts = Arrays.stream(input.split("[ |\t]")).mapToInt(Integer::parseInt).toArray();
		Set<String> states = new HashSet<>();
		String state = Joiner.on("|").join(Arrays.stream(parts).iterator());

		int steps = 0;
		int index = findIndexOfMax(parts);
		while (states.add(state)) {
			int blocks = parts[index];
			parts[index] = 0;

			redistributeBlocks(parts, blocks, index);

			state = Joiner.on("|").join(Arrays.stream(parts).iterator());
			index = findIndexOfMax(parts);

			steps++;
		}

		System.out.println(steps);
	}

	private static void redistributeBlocks(int[] parts, int blocks, int index) {
		while (blocks > 0) {
			if (index < parts.length - 1) {
				index++;
			} else {
				index = 0;
			}

			parts[index] = parts[index] + 1;
			blocks--;
		}
	}

	private static int findIndexOfMax(int[] values) {
		int index = 0;
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < values.length; i++) {
			int value = values[i];
			if (value > max) {
				max = value;
				index = i;
			}
		}

		return index;
	}
}