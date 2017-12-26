public class Exercise3Part1 {

	public static void main(String[] args) {
		double input = 368078;

//		input = 18;

		int size = (int) (Math.floor(Math.sqrt(input)) + 1);
		int square = size * size;
		int center = Math.floorDiv(size, 2);

		int x = size - 1;
		int y = size - 1;
		int direction = 0;
//		int[][] spiral = new int[size][size];

		for (int i = square; i > 0; i--) {
//			spiral[x][y] = i;

//			print(spiral);

			if (input == i) {
				break;
			}

			switch (direction) {
				case 0: {
					if (y == 0) {
						direction = 1;
						x--;
					} else {
						y--;
					}
					break;
				}
				case 1: {
					if (x == 0) {
						direction = 2;
						y++;
					} else {
						x--;
					}
					break;
				}
				case 2: {
					if (y == size - 1) {
						direction = 3;
						x--;
					} else {
						y++;
					}
					break;
				}
				case 3: {
					if (x == size - 1) {
						direction = 0;
						y++;
					} else {
						x++;
					}
					break;
				}
			}
		}

		System.out.println(center + " " + center);
		System.out.println(x + " " + y);
		System.out.println(Math.abs(center - x) + Math.abs(center - y));
	}

	private static void print(int[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				System.out.printf("%4d", data[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}
}