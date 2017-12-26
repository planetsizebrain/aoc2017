import com.google.common.base.Strings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercise15Part2 {

	public static void main(String[] args) throws IOException {
		int count = 0;
		int total = 5000000;
		int leftFactor = 16807;
		int rightFactor = 48271;

		long left = 679;
		long right = 771;

		int matches = 0;
		List<Long> leftMatches = new ArrayList<>();
		List<Long> rightMatches = new ArrayList<>();
		while (count < total) {
			left = generate(left, leftFactor);
			right = generate(right, rightFactor);

			if (count % 100000 == 0) {
				System.out.println(count + " " + matches);
			}

			if (left % 4 == 0) {
				leftMatches.add(left);
			}

			if (right % 8 == 0) {
				rightMatches.add(right);
			}

			if (leftMatches.size() > 0 && rightMatches.size() > 0) {
				count++;

				String leftBits = Strings.padStart(Long.toBinaryString(leftMatches.remove(0)), 16, '0');
				String rightBits = Strings.padStart(Long.toBinaryString(rightMatches.remove(0)), 16, '0');
				String leftBitsTail = leftBits.substring(leftBits.length() - 16, leftBits.length());
				String rightBitsTail = rightBits.substring(rightBits.length() - 16, rightBits.length());
				if (leftBitsTail.equals(rightBitsTail)) {
					matches++;
				}
			}
		}

		System.out.println(matches);
	}

	private static long generate(long number, long factor) {
		return (number * factor) % 2147483647;
	}
}