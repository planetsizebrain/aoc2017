import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import javafx.geometry.Point3D;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise20Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise20.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Pattern pattern = Pattern.compile("(p=)<(.*?)>(, v=)<(.*?)>(, a=)<(.*?)>");

		List<Point3D> positions = new ArrayList<>();
		List<Point3D> velocities = new ArrayList<>();
		List<Point3D> accelerations = new ArrayList<>();
		for (String input : inputs) {
			Matcher matcher = pattern.matcher(input);

			if (matcher.find()) {
				String[] position = matcher.group(2).split(",");
				positions.add(new Point3D(Double.parseDouble(position[0]), Double.parseDouble(position[1]), Double.parseDouble(position[2])));

				String[] velocity = matcher.group(4).split(",");
				velocities.add(new Point3D(Double.parseDouble(velocity[0]), Double.parseDouble(velocity[1]), Double.parseDouble(velocity[2])));

				String[] acceleration = matcher.group(6).split(",");
				accelerations.add(new Point3D(Double.parseDouble(acceleration[0]), Double.parseDouble(acceleration[1]), Double.parseDouble(acceleration[2])));
			}
		}

		Point3D center = new Point3D(0, 0, 0);
		int index = -1;
		int distance = Integer.MAX_VALUE;
		Map<Integer, Long> distances = new HashMap<>();
		for (int i = 0; i < 10000; i++) {
			for (int j = 0; j < positions.size(); j++) {
				Point3D position = positions.remove(j);
				Point3D velocity = velocities.remove(j);
				Point3D acceleration = accelerations.get(j);

				velocity = velocity.add(acceleration);
				position = position.add(velocity);

				positions.add(j, position);
				velocities.add(j, velocity);

				double d = Math.abs(position.getX()) + Math.abs(position.getY()) + Math.abs(position.getZ());
				if (distances.containsKey(j)) {
					distances.put(j, (long) (distances.get(j) + d));
				} else {
					distances.put(j, (long) d);
				}

//				if (d < distance) {
//					distance = (int) d;
//					index = j;
//
//					System.out.println("Closest: " + distance + " " + index + " " + position);
//				}
			}

//			System.out.println("Closest: " + index);
		}

		long d = distances.get(0);
		for (int i = 0; i < distances.size(); i++) {
			if (distances.get(i) < d) {
				index = i;
				d = distances.get(i);
				System.out.println(d + " " + index);
			}
		}

		System.out.println(index);
	}
}