import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Exercise22Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise22.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		int size = 2500;
		int offset = 1250;
		Table<Integer, Integer, Character> matrix = HashBasedTable.create(size, size);
		for (int i = 0; i < inputs.size(); i++) {
			String input = inputs.get(i);
			for (int j = 0; j < input.length(); j++) {
				matrix.put(i + offset, j + offset, input.charAt(j));
			}
		}

//		print(matrix, size);

		int x = (int) (offset + Math.floor(inputs.size() / 2));
		int y = (int) (offset + Math.floor(inputs.size() / 2));
		char direction = 'u';
		int infections = 0;
		int iterations = 10000;
		for (int i = 0; i < iterations; i++) {
			char c = '.';
			if (matrix.contains(x, y)) {
				c = matrix.get(x, y);
			}

			direction = getDirection(direction, c == '#');
			if (c == '.') {
				infections++;
			}
			matrix.put(x, y, c == '#' ? '.' : '#');

//			print(matrix, size);

			switch (direction) {
				case 'u': {
					x -= 1;
					break;
				}
				case 'd': {
					x += 1;
					break;
				}
				case 'l': {
					y -= 1;
					break;
				}
				case 'r': {
					y += 1;
					break;
				}
			}
		}

		System.out.println(infections);
	}

	private static char getDirection(char direction, boolean infected) {
		if (infected) {
			switch (direction) {
				case 'u': {
					return 'r';
				}
				case 'd': {
					return 'l';
				}
				case 'l': {
					return 'u';
				}
				case 'r': {
					return 'd';
				}
			}
		} else {
			switch (direction) {
				case 'u': {
					return 'l';
				}
				case 'd': {
					return 'r';
				}
				case 'l': {
					return 'd';
				}
				case 'r': {
					return 'u';
				}
			}
		}

		return ' ';
	}

	private static void print(Table<Integer, Integer, Character> screen, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (screen.contains(i, j)) {
					System.out.print(screen.get(i, j));
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}

		System.out.println();
	}
}