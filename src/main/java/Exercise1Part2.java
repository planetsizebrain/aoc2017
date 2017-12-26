import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class Exercise1Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise1.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

//		input = "12131415";

		int total = 0;
		int length = input.length();
		for (int i = 0; i < length; i++) {
			int index = i + (length / 2);
			if (index >= length) {
				index -= length;
			}

			char currentChar = input.charAt(i);
			char nextChar = input.charAt(index);

			if (currentChar == nextChar) {
				total += Integer.parseInt("" + currentChar);
			}
		}

		System.out.println(total);
	}
}