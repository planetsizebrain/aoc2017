import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercise17Part2 {

	public static void main(String[] args) throws IOException {
		int steps = 376;
		int position = 0;
		int valueAfterZero = -1;

		for (int i = 1; i <= 50000000; i++) {
			position = ((position + steps) % i) + 1;

			if (position == 1) {
				valueAfterZero = i;
			}
		}

		System.out.println(valueAfterZero);
	}
}