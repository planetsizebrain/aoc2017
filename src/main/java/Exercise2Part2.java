import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Exercise2Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise2.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		int checksum = 0;
		for (String line : lines) {
			String[] parts = line.split("[ |\t]");
			int[] numbers = Arrays.stream(parts)
					.mapToInt(Integer::parseInt)
					.toArray();

			for (int i = 0; i < numbers.length; i++) {
				for (int j = i + 1; j < numbers.length; j++) {
					int number1 = numbers[i];
					int number2 = numbers[j];
					if (number2 > number1) {
						int temp = number1;
						number1 = number2;
						number2 = temp;
					}

					if (Math.abs(number1 % number2) == 0) {
						checksum += Math.abs(number1 / number2);
						break;
					}
				}
			}
		}

		System.out.println(checksum);
	}
}