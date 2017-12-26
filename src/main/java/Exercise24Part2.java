import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise24Part2 {

	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise24.txt");
		List<String> inputs = Resources.readLines(url, Charsets.UTF_8);

		List<Port> ports = inputs.stream().map(Exercise24Part2::toPort).collect(Collectors.toList());

		List<Port> startPorts = ports.stream().filter(p -> p.left == 0 || p.right == 0).collect(Collectors.toList());
		List<List<Port>> bridges = new ArrayList<>();
		for (Port port : startPorts) {
			List<Port> visited = new ArrayList<>();
			visited.add(port);
			int nextValue = port.left == 0 ? port.right : port.left;

			dfs(nextValue, ports, visited, bridges);
		}

		int length = bridges.stream().max(Comparator.comparing(List::size)).get().size();

		List<Port> bridge = bridges.stream()
				.filter(list -> list.size() == length)
				.max(Comparator.comparing(list -> list.stream().map(p -> p.left + p.right).reduce(0, Integer::sum)))
				.get();


		System.out.println(bridge.stream().map(p -> p.left + p.right).reduce(0, Integer::sum));
	}

	private static void dfs(int value, List<Port> ports, List<Port> visited, List<List<Port>> bridges) {
		List<Port> neighbours = getNeighbours(value, ports, visited);
		if (neighbours.size() > 0) {
			for (Port neighbour : neighbours) {
				int nextValue = neighbour.left == value ? neighbour.right : neighbour.left;
				List<Port> done = new ArrayList<>(visited);
				done.add(neighbour);
				dfs(nextValue, ports, done, bridges);
			}
		} else {
			bridges.add(new ArrayList<>(visited));
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