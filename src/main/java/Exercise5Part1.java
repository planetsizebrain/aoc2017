import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Exercise5Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise5.txt");
		List<String> lines = Resources.readLines(url, Charsets.UTF_8);

		int steps = 0;
		int index = 0;
		int[] jumps = lines.stream().mapToInt(Integer::parseInt).toArray();

		while (index > -1 && index < lines.size()) {
			int jump = jumps[index];
			jumps[index] = jump + 1;

			index += jump;
			steps++;
		}

		System.out.println(steps);
	}
}