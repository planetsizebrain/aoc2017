import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class Exercise1Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise1.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

//		input = "91212129";

		int total = 0;
		int count = 0;
		int length = input.length();

		char currentChar = input.charAt(0);
		for (int i = 1; i <= length; i++) {
			int index = i;
			if (index == length) {
				index = 0;
			}

			char nextChar = input.charAt(index);

			if (currentChar == nextChar) {
				count++;
				if (index == 0) {
					total += count * Integer.parseInt("" + currentChar);
				}
			} else {
				total += count * Integer.parseInt("" + currentChar);
				count = 0;
			}

			currentChar = nextChar;
		}

		System.out.println(total);
	}
}