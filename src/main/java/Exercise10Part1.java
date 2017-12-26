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
import java.util.stream.Stream;

public class Exercise10Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise10.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

		int maxValue = 255;
		List<Integer> values = IntStream.iterate(0, i -> i + 1).limit(maxValue + 1).boxed().collect(Collectors.toList());

		int currentPos = 0;
		int skipSize = 0;
		int[] lengths = Arrays.stream(input.split(",")).mapToInt(v -> Integer.parseInt(v.trim())).toArray();

		for (int i = 0; i < lengths.length; i++) {
			int lengthSize = lengths[i];

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

		System.out.println(values.get(0) * values.get(1));
	}
}