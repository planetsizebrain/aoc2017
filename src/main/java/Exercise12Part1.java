import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Exercise12Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise12.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Multimap<Integer, Integer> memory = HashMultimap.create();
		for (String input : inputs) {
			String[] split = input.split("<->");
			int bank = Integer.parseInt(split[0].trim());
			List<Integer> banks = Arrays.stream(split[1].trim().split(", ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

			memory.putAll(bank, banks);
		}

		int count = 0;
		for (Integer bank : memory.keySet()) {
			if (bank == 0) {
				count++;
			} else {
				Collection<Integer> banks = memory.get(bank);
				if (banks.contains(0)) {
					count++;
				} else {
					List<Integer> visited = new ArrayList<>();
					List<Integer> toVisit = new ArrayList<>();
					toVisit.addAll(banks);

					while (toVisit.size() > 0) {
						int b = toVisit.remove(0);
						if (!visited.contains(b)) {
							Collection nb = memory.get(b);
							if (nb.contains(0)) {
								count++;
								break;
							} else {
								visited.add(b);
								toVisit.addAll(0, nb);
							}
						}
					}
				}
			}
		}

		System.out.println(count);
	}
}