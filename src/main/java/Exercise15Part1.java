import com.google.common.base.Strings;

import java.io.IOException;

public class Exercise15Part1 {

	public static void main(String[] args) throws IOException {
		int maxRuns = 40000000;
		int leftFactor = 16807;
		int rightFactor = 48271;

		long left = 679;
		long right = 771;
		int matches = 0;
		for (int i = 0; i < maxRuns; i++) {
			left = generate(left, leftFactor);
			right = generate(right, rightFactor);

//			System.out.println(left + " " + right);

			String leftBits = Strings.padStart(Long.toBinaryString(left), 16, '0');
			String rightBits = Strings.padStart(Long.toBinaryString(right), 16, '0');
			String leftBitsTail = leftBits.substring(leftBits.length() - 16, leftBits.length());
			String rightBitsTail = rightBits.substring(rightBits.length() - 16, rightBits.length());
			if (leftBitsTail.equals(rightBitsTail)) {
				matches++;
			}
		}

		System.out.println(matches);
	}

	private static long generate(long number, long factor) {
		return (number * factor) % 2147483647;
	}
}