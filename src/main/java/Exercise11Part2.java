import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class Exercise11Part2 {

	// https://www.redblobgames.com/grids/hexagons/#distances
	public static void main(String[] args) throws IOException {
		URL url = Resources.getResource("exercise11.txt");
		String input = Resources.toString(url, Charsets.UTF_8);

		String[] moves = input.split(",");

		int x = 0;
		int y = 0;
		int z = 0;
		int max = 0;

		for (int i = 0; i < moves.length; i++) {
			String move = moves[i];

			switch (move) {
				case "n": {
					x += 1;
					z -= 1;
					break;
				}
				case "ne": {
					x += 1;
					y -= 1;
					break;
				}
				case "se": {
					y -= 1;
					z += 1;
					break;
				}
				case "s": {
					x -= 1;
					z += 1;
					break;
				}
				case "sw": {
					x -= 1;
					y += 1;
					break;
				}
				case "nw": {
					y += 1;
					z -= 1;
					break;
				}
			}

			int distance = (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2;
			if (distance > max) {
				max = distance;
			}
		}

		System.out.println(max);
	}
}