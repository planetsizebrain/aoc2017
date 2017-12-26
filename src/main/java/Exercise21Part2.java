import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Exercise21Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise21.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		HashMap<String, String> rules = new HashMap<>();
		for (String input : inputs) {
			String rule = input.substring(0, input.indexOf(" => "));
			String replacement = input.substring(input.indexOf(" => ") + 4, input.length());

			List<String> permutations = getPermutations(rule);
			for (String permutation : permutations) {
				rules.put(permutation, replacement);
			}
		}

		Table<Integer, Integer, Character> screen = HashBasedTable.create();
		screen.put(0, 0, '.');
		screen.put(0, 1, '#');
		screen.put(0, 2, '.');
		screen.put(1, 0, '.');
		screen.put(1, 1, '.');
		screen.put(1, 2, '#');
		screen.put(2, 0, '#');
		screen.put(2, 1, '#');
		screen.put(2, 2, '#');

		int iterations = 18;
		for (int i = 0; i < iterations; i++) {
			int length = 0;
			int size = (int) Math.sqrt(screen.size());
			if (size % 3 == 0) {
				length = 3;
			}
			if (size % 2 == 0) {
				length = 2;
			}

			Table<Integer, Integer, Character> newScreen = HashBasedTable.create();

			for (int j = 0; j < size / length; j++) {
				for (int k = 0; k < size / length; k++) {
					StringBuilder rule = new StringBuilder();

					for (int l = 0; l < length; l++) {
						if (l != 0) {
							rule.append('/');
						}

						for (int m = 0; m < length; m++) {
							rule.append(screen.get((j * length) + l, (k * length) + m));
						}
					}

					String replacement = rules.get(rule.toString());
					String[] rows = replacement.split("/");
					for (int l = 0; l < rows.length; l++) {
						char[] columns = rows[l].toCharArray();
						for (int m = 0; m < columns.length; m++) {
							newScreen.put((j * rows.length) + l, (k * rows.length) + m, columns[m]);
						}
					}
				}
			}

			screen = newScreen;
		}

		System.out.println(getActivePixelCount(screen));
	}

	private static List<String> getPermutations(String rule) {
		List<String> permutations = new ArrayList<>();

		String[] parts = rule.split("/");
		char[][] matrix = new char[parts.length][parts.length];
		for (int i = 0; i < parts.length; i++) {
			char[] row = parts[i].toCharArray();
			for (int j = 0; j < row.length; j++) {
				matrix[i][j] = row[j];
			}
		}

		// https://codereview.stackexchange.com/questions/120406/rotate-matrix-90-degrees-clockwise
		for (int i = 0; i < 4; i++) {
			getTranspose(matrix);
			flipHorizontally(matrix);

			permutations.add(toRule(matrix));

			flipHorizontally(matrix);

			permutations.add(toRule(matrix));

			flipHorizontally(matrix);
			flipVertically(matrix);

			permutations.add(toRule(matrix));

			flipVertically(matrix);
		}

		return permutations;
	}

	private static String toRule(char[][] matrix) {
		StringBuilder rule = new StringBuilder();

		for (int i = 0; i < matrix.length; i++) {
			if (i != 0) {
				rule.append("/");
			}

			for (int j = 0; j < matrix.length; j++) {
				rule.append(matrix[i][j]);
			}
		}

		return rule.toString();
	}

	private static void getTranspose(char[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix.length ; j++) {
				char temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
	}

	private static void flipHorizontally(char[][] matrix) {
		int len = matrix.length ;
		for (int i = 0; i < len / 2; i++) {
			for (int j = 0;j < len; j++) {
				char temp = matrix[i][j];
				matrix[i][j] = matrix[len - 1 - i][j];
				matrix[len - 1 - i][j] = temp;
			}
		}
	}

	private static void flipVertically(char[][] matrix) {
		int len = matrix.length ;
		for (int i = 0; i < len; i++) {
			for (int j = 0;j < len / 2; j++) {
				char temp = matrix[i][j];
				matrix[i][j] = matrix[i][len - 1 - j];
				matrix[i][len - 1 - j] = temp;
			}
		}
	}

	private static long getActivePixelCount(Table<Integer, Integer, Character> screen) {
		return screen.values().stream().filter(c -> c == '#').count();
	}

	private static void print(Table<Integer, Integer, Character> screen) {
		int size = (int) Math.sqrt(screen.size());
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(screen.get(i, j));
			}
			System.out.println();
		}

		System.out.println();
	}
}