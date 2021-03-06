import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Exercise4Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise4.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		int valid = 0;
		for (String line : lines) {
			String[] parts = line.split("[ |\t]");

			Set<String> words = new HashSet<>();

			for (String part : parts) {
				char[] chars = part.toCharArray();
				Arrays.sort(chars);

				words.add(new String(chars));
			}

			if (parts.length == words.size()) {
				valid++;
			}
		}

		System.out.println(valid);
	}
}