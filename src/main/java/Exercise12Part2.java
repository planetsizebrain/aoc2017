import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercise12Part2 {

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

		Multimap<Integer, Integer> groups = HashMultimap.create();
		for (Integer bank : memory.keySet()) {
			if (!groups.containsValue(bank) && !groups.containsKey(bank)) {
				Set<Integer> group = new HashSet<>();
				Collection<Integer> banks = memory.get(bank);

				group.add(bank);
				visit(banks, group, memory);

				groups.putAll(bank, group);
			}
		}

		System.out.println(groups.keySet().size());
	}

	private static void visit(Collection<Integer> banks, Set<Integer> group, Multimap<Integer, Integer> memory) {
		for (Integer bank : banks) {
			if (!group.contains(bank)) {
				group.add(bank);
				Collection<Integer> children = memory.get(bank);

				visit(children, group, memory);
			}
		}
	}
}