import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.io.Resources;
import javafx.geometry.Point3D;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Exercise20Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise20.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		Pattern pattern = Pattern.compile("(p=)<(.*?)>(, v=)<(.*?)>(, a=)<(.*?)>");

		List<Point> positions = new ArrayList<>();
		for (String input : inputs) {
			Matcher matcher = pattern.matcher(input);

			if (matcher.find()) {
				Point point = new Point();

				String[] position = matcher.group(2).split(",");
				point.point = new Point3D(Double.parseDouble(position[0]), Double.parseDouble(position[1]), Double.parseDouble(position[2]));

				String[] velocity = matcher.group(4).split(",");
				point.velocity = new Point3D(Double.parseDouble(velocity[0]), Double.parseDouble(velocity[1]), Double.parseDouble(velocity[2]));

				String[] acceleration = matcher.group(6).split(",");
				point.acceleration = new Point3D(Double.parseDouble(acceleration[0]), Double.parseDouble(acceleration[1]), Double.parseDouble(acceleration[2]));

				positions.add(point);
			}
		}

		int size = positions.size();
		for (int i = 0; i < 10000; i++) {
			Multimap<Point3D, Point> filtered = ArrayListMultimap.create(positions.size(), 1);
			for (Point p : positions) {
				Point3D position = p.point;
				Point3D velocity = p.velocity;
				Point3D acceleration = p.acceleration;

				p.velocity = velocity.add(acceleration);
				p.point = position.add(p.velocity);

				filtered.put(p.point, p);
			}

			positions.clear();
			for (Point3D key : filtered.keySet()) {
				Collection<Point> points = filtered.get(key);
				if (points.size() == 1) {
					positions.addAll(points);
				} else {
					System.out.println("Collision: " + key);
				}
			}

			if (positions.size() < size) {
				size = positions.size();
				System.out.println(size);
			}
		}

		System.out.println(size);
	}

	private static class Point {

		public Point3D point;
		public Point3D velocity;
		public Point3D acceleration;
	}
}