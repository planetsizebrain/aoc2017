import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.primitives.Longs;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise19Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise19.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Point position = new Point(0, 0);
		char direction = 'd';
		char[][] grid = new char[inputs.size()][inputs.get(0).length()];
		for (int i = 0; i < inputs.size(); i++) {
			String input = inputs.get(i);
			for (int j = 0; j < input.length(); j++) {
				char c = input.charAt(j);
				grid[i][j] = c;

				if (i == 0 && c == '|') {
					position.y = j;
				}
			}
		}

		boolean done = false;
		StringBuilder result = new StringBuilder();
		position = getNextPosition(position, direction, grid);
		while (position != null && !done) {
			char c = grid[position.x][position.y];

			switch (c) {
				case '|':
				case '-': {
					break;
				}
				case '+': {
					if (direction == 'u' || direction == 'd') {
						if (position.y > 0 && grid[position.x][position.y - 1] != ' ') {
							direction = 'l';
						}
						if (position.y < grid[0].length - 1 && grid[position.x][position.y + 1] != ' ') {
							direction = 'r';
						}
					} else if (direction == 'l' || direction == 'r') {
						if (position.x > 0 && grid[position.x - 1][position.y] != ' ') {
							direction = 'u';
						}
						if (position.x < grid.length - 1 && grid[position.x + 1][position.y] != ' ') {
							direction = 'd';
						}
					} else {
						done = true;
					}
					break;
				}
				case ' ': {
					done = true;
					break;
				}
				default: {
					result.append(c);
				}
			}

			position = getNextPosition(position, direction, grid);
		}

		System.out.println(result);
	}

	private static Point getNextPosition(Point position, char direction, char[][] grid) {
		switch (direction) {
			case 'd': {
				if (position.x < grid.length - 1) {
					return new Point(position.x + 1, position.y);
				}
			}
			case 'u': {
				if (position.x > 0) {
					return new Point(position.x - 1, position.y);
				}
				break;
			}
			case 'l': {
				if (position.y > 0) {
					return new Point(position.x, position.y - 1);
				}
				break;
			}
			case 'r': {
				if (position.y < grid[0].length - 1) {
					return new Point(position.x, position.y + 1);
				}
				break;
			}
		}

		return null;
	}
}