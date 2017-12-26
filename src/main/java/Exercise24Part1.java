import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Exercise24Part1 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise24.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		List<Port> ports = inputs.stream().map(Exercise24Part1::toPort).collect(Collectors.toList());

		List<Port> startPorts = ports.stream().filter(p -> p.left == 0 || p.right == 0).collect(Collectors.toList());
		List<Integer> strengths = new ArrayList<>();
		for (Port port : startPorts) {
			List<Port> visited = new ArrayList<>();
			visited.add(port);
			int nextValue = port.left == 0 ? port.right : port.left;

			dfs(nextValue, ports, visited, strengths);
		}

		System.out.println(strengths.stream().max(Integer::compareTo).get());
	}

	private static void dfs(int value, List<Port> ports, List<Port> visited, List<Integer> strengths) {
		List<Port> neighbours = getNeighbours(value, ports, visited);
		if (neighbours.size() > 0) {
			for (Port neighbour : neighbours) {
				int nextValue = neighbour.left == value ? neighbour.right : neighbour.left;
				List<Port> done = new ArrayList<>(visited);
				done.add(neighbour);
				dfs(nextValue, ports, done, strengths);
			}
		} else {
			strengths.add(visited.stream().map(p -> p.left + p.right).reduce(0, Integer::sum));
		}
	}

	private static List<Port> getNeighbours(int nextValue, List<Port> ports, List<Port> visited) {
		return ports.stream().filter(p -> !visited.contains(p) && (p.left == nextValue || p.right == nextValue)).collect(Collectors.toList());
	}

	private static Port toPort(String input) {
		String[] parts = input.split("/");

		return new Port(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	private static class Port {

		int left;
		int right;

		Port(int left, int right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Port)) return false;
			Port port = (Port) o;
			return (left == port.left &&
					right == port.right) || (left == port.right &&
					right == port.left);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(left, right);
		}
	}
}