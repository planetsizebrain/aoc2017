import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Exercise22Part2 {

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
		int iterations = 10000000;
		for (int i = 0; i < iterations; i++) {
			char c = '.';
			if (matrix.contains(x, y)) {
				c = matrix.get(x, y);
			}

			direction = getDirection(direction, c);
			if (c == 'W') {
				infections++;
			}
			matrix.put(x, y, getState(c));

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

	private static char getState(char state) {
		switch (state) {
			case '.': {
				return 'W';
			}
			case 'W': {
				return '#';
			}
			case '#': {
				return 'F';
			}
			case 'F': {
				return '.';
			}
		}

		System.out.println("WAT!");

		return ' ';
	}

	private static char getDirection(char direction, char state) {
		if (state == '#') {
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
		}

		if (state == '.') {
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

		if (state == 'F') {
			switch (direction) {
				case 'u': {
					return 'd';
				}
				case 'd': {
					return 'u';
				}
				case 'l': {
					return 'r';
				}
				case 'r': {
					return 'l';
				}
			}
		}

		return direction;
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