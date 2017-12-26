import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class Exercise9Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise9.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

		int garbage = 0;
		boolean inGarbage = false;
		for (int i = 1; i < input.length(); i++) {
			char current = input.charAt(i);

			if (!inGarbage && current != '!') {
				switch (current) {
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
			} else {
				garbage++;
			}
		}

		System.out.println(garbage);
	}
}