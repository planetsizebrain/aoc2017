import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Exercise2Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise2.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		int checksum = 0;
		for (String line : lines) {
			String[] parts = line.split("[ |\t]");
			int min = Arrays.stream(parts)
					.mapToInt(Integer::parseInt)
					.min()
					.getAsInt();

			int max = Arrays.stream(parts)
					.mapToInt(Integer::parseInt)
					.max()
					.getAsInt();

			checksum += max - min;
		}

		System.out.println(checksum);
	}
}