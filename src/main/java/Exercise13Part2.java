import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.tools.javac.code.Lint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Exercise13Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise13.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Map<Integer, Integer> layers = new HashMap<>();
		for (String input : inputs) {
			String[] split = input.split(": ");
			int depth = Integer.parseInt(split[0].trim());
			int range = Integer.parseInt(split[1].trim());

			layers.put(depth, range);
		}

		int maxDepth = layers.keySet().stream().max(Integer::compareTo).get();
		int[] scanners = new int[maxDepth + 1];
		boolean[] directions = new boolean[maxDepth + 1];

		int delay = 0;
		boolean caught;
		int[] delayedScanners = Arrays.copyOf(scanners, scanners.length);
		boolean[] delayedDirections = Arrays.copyOf(directions, directions.length);

		do {
			if (delay % 10000 == 0) {
				System.out.println(delay);
			}

			delay++;
			caught = false;

			delayScanners(1, delayedScanners, delayedDirections, layers);

			int[] delayedScanners2 = Arrays.copyOf(delayedScanners, delayedScanners.length);
			boolean[] delayedDirections2 = Arrays.copyOf(delayedDirections, delayedDirections.length);

			for (int i = 0; i < delayedScanners2.length; i++) {
				if (layers.containsKey(i)) {
					if (delayedScanners2[i] == 0) {
						caught = true;
						break;
					}
				}

				delayScanners(1, delayedScanners2, delayedDirections2, layers);
			}
		} while (caught);

		System.out.println(delay);
	}

	private static void delayScanners(int delay, int[] scanners, boolean[] directions, Map<Integer, Integer> layers) {
		for (int i = 0; i < delay; i++) {
			for (int j = 0; j < scanners.length; j++) {
				if (layers.containsKey(j)) {
					int depth = layers.get(j);

					if (directions[j]) {
						if (scanners[j] == 0) {
							directions[j] = false;
							scanners[j] = 1;
						} else {
							scanners[j] = scanners[j] - 1;
						}
					} else {
						if (scanners[j] == depth - 1) {
							directions[j] = true;
							scanners[j] = depth - 2;
						} else {
							scanners[j] = scanners[j] + 1;
						}
					}
				}
			}
		}
	}
}