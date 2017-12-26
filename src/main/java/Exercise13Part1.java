import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Exercise13Part1 {

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

		int severity = 0;
		for (int i = 0; i < scanners.length; i++) {
			if (layers.containsKey(i)) {
				if (scanners[i] == 0) {
					System.out.println("Caught  ");
					severity += i * layers.get(i);
				}
			}

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

		System.out.println(severity);
	}
}