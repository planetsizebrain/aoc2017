import com.google.common.base.Strings;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Exercise14Part2 {

	public static void main(String[] args) throws IOException {
		String prefix = "nbysizxe";
		int[][] matrix = new int[128][128];

		for (int s = 0; s < 128; s++) {
			String input = prefix + "-" + s;
			int maxValue = 255;
			List<Integer> values = IntStream.iterate(0, i -> i + 1).limit(maxValue + 1).boxed().collect(Collectors.toList());

			int currentPos = 0;
			int skipSize = 0;
			List<Integer> lengths = new ArrayList<>();
			for (int i = 0; i < input.length(); i++) {
				lengths.add((int) input.charAt(i));
			}

			lengths.add(17);
			lengths.add(31);
			lengths.add(73);
			lengths.add(47);
			lengths.add(23);

			for (int r = 0; r < 64; r++) {
				for (int i = 0; i < lengths.size(); i++) {
					int lengthSize = lengths.get(i);

					List newValues = new ArrayList(values.size());
					if (currentPos + lengthSize >= values.size()) {
						List<Integer> subList = new ArrayList<>(values.subList(currentPos, values.size()));
						subList.addAll(values.subList(0, lengthSize - (values.size() - currentPos)));
						Collections.reverse(subList);
						newValues.addAll(subList.subList(0, values.size() - currentPos));
						newValues.addAll(0, values.subList(lengthSize - (values.size() - currentPos), currentPos));
						newValues.addAll(0, subList.subList(values.size() - currentPos, subList.size()));
					} else {
						newValues.addAll(values.subList(0, currentPos));
						List<Integer> subList = new ArrayList<>(values.subList(currentPos, currentPos + lengthSize));
						Collections.reverse(subList);
						newValues.addAll(subList);
						newValues.addAll(values.subList(currentPos + lengthSize, values.size()));
					}

					values = newValues;
					currentPos = (currentPos + lengthSize + skipSize) % values.size();
					skipSize++;
				}
			}

			List<Integer> xors = new ArrayList<>();
			for (int i = 0; i < 16; i++) {
				List<Integer> sublist = values.subList(i * 16, (i * 16) + 16);
				xors.add(sublist.stream().reduce(0, (x, y) -> x ^ y));
			}

			String hash = xors.stream().map(n -> String.format("%02x", n)).reduce("", (x, y) -> x += y);
			String bits = "";

			for (int j = 0; j < 32; j++) {
				char c = hash.charAt(j);
				int number = Integer.parseInt("" + c, 16);
				String binary = Integer.toBinaryString(number);
				bits += Strings.padStart(binary, 4, '0');
			}

			for (int k = 0; k < bits.length(); k++) {
				if (bits.charAt(k) == '1') {
					matrix[s][k] = 1;
				}
			}

			System.out.println(bits);
		}

		int regions = findRegions(matrix);

		System.out.println(regions);
	}

	private static int findRegions(int[][] matrix) {
		int regions = 0;

		Point start = findStart(matrix);
		do {
			regions++;

			markContiguousRegion(start, matrix);

			clear(matrix);

			start = findStart(matrix);
		} while (start != null);

		return regions;
	}

	private static void clear(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == -1) {
					matrix[i][j] = 0;
				}
			}
		}
	}

	private static void markContiguousRegion(Point start, int[][] matrix) {
		if (matrix[start.x][start.y] > 0) {
			matrix[start.x][start.y] = -1;

			if (start.x > 0) {
				markContiguousRegion(new Point(start.x - 1, start.y), matrix);
			}
			if (start.x < 127) {
				markContiguousRegion(new Point(start.x + 1, start.y), matrix);
			}
			if (start.y > 0) {
				markContiguousRegion(new Point(start.x, start.y - 1), matrix);
			}
			if (start.y < 127) {
				markContiguousRegion(new Point(start.x, start.y + 1), matrix);
			}
		}
	}

	private static Point findStart(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] > 0) {
					return new Point(i, j);
				}
			}
		}

		return null;
	}
}