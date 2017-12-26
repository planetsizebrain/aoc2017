import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Exercise16Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise16.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

//		input = "s1,x3/4,pe/b";

		String[] moves = input.split(",");

		String positions = "abcdefghijklmnop";
//		positions = "abcde";
		List<Character> dancers = new ArrayList<>();
		for (int i = 0; i < positions.length(); i++) {
			dancers.add(positions.charAt(i));
		}

		Set<String> visited = new HashSet<>();
		for (int i = 0; i < 1000000000; i++) {
			for (String move : moves) {
				char operation = move.charAt(0);
				String params = move.substring(1);

				switch (operation) {
					case 's': {
						int distance = Integer.parseInt(params);
						Collections.rotate(dancers, distance);

						break;
					}
					case 'x': {
						String[] indexes = params.split("/");
						int first = Integer.parseInt(indexes[0]);
						int second = Integer.parseInt(indexes[1]);
						Collections.swap(dancers, first, second);

						break;
					}
					case 'p': {
						String[] indexes = params.split("/");
						Character first = indexes[0].charAt(0);
						Character second = indexes[1].charAt(0);
						Collections.swap(dancers, dancers.indexOf(first), dancers.indexOf(second));

						break;
					}
				}
			}

			if (!visited.add(dancers.stream().map(Object::toString).reduce("", (a, b) -> a + b))) {
				i = 1000000000 - (1000000000 % i);
			}
		}

		System.out.println(dancers.stream().map(Object::toString).reduce("", (a, b) -> a + b));
	}
}