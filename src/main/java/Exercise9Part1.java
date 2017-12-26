import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise9Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise9.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

		int groups = 0;
		int level = 1;
		boolean inGarbage = false;
		for (int i = 1; i < input.length(); i++) {
			char current = input.charAt(i);

			if (!inGarbage && current != '!') {
				switch (current) {
					case '{': {
						level++;
						break;
					}
					case '}': {
						groups += level;
						level--;
						break;
					}
					case '<': {
						inGarbage = true;
						break;
					}
					case '>': {
						inGarbage = false;
						break;
					}
				}
			} else if (current == '!') {
				if (i < input.length() - 2) {
					i++;
				}
			} else if (current == '>') {
				inGarbage = false;
			}
		}

		System.out.println(groups);
	}
}