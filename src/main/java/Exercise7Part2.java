import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Exercise7Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise7.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		Multimap<String, String> discs = ArrayListMultimap.create();
		Multimap<String, String> discs2 = ArrayListMultimap.create();
		HashMap<String, Integer> weights = new HashMap<>();

		for (String line : lines) {
			String[] parts = line.split("->");

			String key = parts[0].substring(0, parts[0].indexOf(' '));
			int weight = Integer.parseInt(parts[0].substring(parts[0].indexOf('(') + 1, parts[0].indexOf(')')));
			weights.put(key, weight);

			if (parts.length > 1) {
				String[] values = parts[1].trim().split(",");

				for (String value : values) {
					discs.put(key, value.trim());
					discs2.put(key, value.trim());
				}
			}
		}

		while (discs.keySet().size() > 1) {
			List<String> toRemove = new ArrayList<>();
			for (String key : discs.keySet()) {
				Collection<String> values = discs.get(key);

				boolean found = false;
				for (String value : values) {
					if (discs.containsKey(value)) {
						found = true;
					}
				}

				if (!found) {
					toRemove.add(key);
				}
			}

			for (String key : toRemove) {
				discs.removeAll(key);
			}
		}

		String root = discs.keySet().iterator().next();
		int targetWeight = 0;
		int difference = 0;
		boolean equal = false;
		while (!equal) {
			List<String> keys = new ArrayList<>();
			List<Integer> calculatedWeights = new ArrayList<>();

			for (String key : discs2.get(root)) {
				keys.add(key);

				Integer weight = calculateWeight(Lists.newArrayList(key), discs2, weights);
				calculatedWeights.add(weight);

				System.out.println(key + " " + weight);
			}

			System.out.println();

			if (calculatedWeights.stream().distinct().limit(2).count() <= 1) {
				// all equal
				equal = true;
				targetWeight = targetWeight - calculatedWeights.stream().mapToInt(Integer::intValue).sum() - difference;
			} else {
				int index = 0;
				int max = Integer.MIN_VALUE;

				for (int i = 0; i < calculatedWeights.size(); i++) {
					int value = calculatedWeights.get(i);
					if (value > max) {
						max = value;
						index = i;
					}
				}

				targetWeight = max;
				difference = max - calculatedWeights.get(index > 0 ? 0 : 1);
				root = keys.get(index);
			}
 		}

		System.out.println(targetWeight);
	}

	private static int calculateWeight(Collection<String> parts, Multimap<String, String> discs, HashMap<String, Integer> weights) {
		int weight = 0;

		for (String key : parts) {
			weight += weights.get(key);

			if (discs.containsKey(key)) {
				weight += calculateWeight(discs.get(key), discs, weights);
			}
		}

		return weight;
	}
}