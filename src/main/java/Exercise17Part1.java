import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Exercise17Part1 {

	public static void main(String[] args) throws IOException {
		int steps = 376;
		int position = 0;
		int value = 0;
		List<Integer> values = Lists.newArrayList(value++);

		for (int i = 0; i < 2017; i++) {
			int index = (position + steps) % values.size();
			position = index + 1;
			values.add(position, value++);
		}

		System.out.println(values.get(values.indexOf(2017) + 1));
	}
}