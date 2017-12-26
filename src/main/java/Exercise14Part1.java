import com.google.common.base.Strings;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Exercise14Part1 {

	public static void main(String[] args) throws IOException {
		String prefix = "nbysizxe";
		int squares = 0;

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
				for (int k = 0; k < binary.length(); k++) {
					if (binary.charAt(k) == '1') {
						squares++;
					}
				}
			}

			System.out.println(bits);
		}

		System.out.println(squares);
	}
}