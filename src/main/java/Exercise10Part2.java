import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Exercise10Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise10.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

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

		System.out.println(xors.stream().map(n -> String.format("%02x", n)).reduce("", (x, y) -> x += y));
	}
}