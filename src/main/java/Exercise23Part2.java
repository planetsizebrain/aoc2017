import com.google.common.math.LongMath;

import java.io.IOException;

public class Exercise23Part2 {

	public static void main(String[] args) throws IOException {
		// https://github.com/dp1/AoC17/blob/master/day23.5.txt

		long b = (65 * 100) + 100000;
		long c = b + 17000;
		long h = 0;
		for (long i = b; i <= c; i += 17) {
			if (!LongMath.isPrime(i)) {
				h++;
			}
		}

		System.out.println(h);
	}
}