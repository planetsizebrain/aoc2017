import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Exercise7Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise7.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		Multimap<String, String> discs = ArrayListMultimap.create();
		for (String line : lines) {
			String[] parts = line.split("->");

			if (parts.length > 1) {
				String key = parts[0].substring(0, parts[0].indexOf(' '));
				String[] values = parts[1].trim().split(",");

				for (String value : values) {
					discs.put(key, value.trim());
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

		System.out.println(discs.keySet().iterator().next());
	}
}