public class Exercise3Part2 {

	public static void main(String[] args) {
		double input = 368078;

//		input = 18;

		int size = (int) (Math.floor(Math.sqrt(input)) + 1);
		int square = size * size;
		int center = Math.floorDiv(size, 2);

		int x = center - 1;
		int y = center + 1;
		int direction = 1;
		int[][] spiral = new int[size][size];

		spiral[center][center] = 1;
		spiral[center][center + 1] = 1;

		int count = 0;
		for (int i = 0; i < square; i++) {
			count = count(spiral, x, y, size);
			spiral[x][y] = count;

//			print(spiral);

			if (count > input) {
				break;
			}

			switch (direction) {
				case 0: {
					if (y == 0 || spiral[x + 1][y] == 0) {
						direction = 3;
						x++;
					} else {
						y--;
					}
					break;
				}
				case 1: {
					if (x == 0 || spiral[x][y - 1] == 0) {
						direction = 0;
						y--;
					} else {
						x--;
					}
					break;
				}
				case 2: {
					if (y == size - 1 || spiral[x - 1][y] == 0) {
						direction = 1;
						x--;
					} else {
						y++;
					}
					break;
				}
				case 3: {
					if (x == size - 1 || spiral[x][y + 1] == 0) {
						direction = 2;
						y++;
					} else {
						x++;
					}
					break;
				}
			}
		}

		System.out.println(count);
	}

	private static int count(int[][] spiral, int x, int y, int size) {
		int count = 0;

		if (x - 1 > 0 && y - 1 > 0) {
			count += spiral[x - 1][y - 1];
		}
		if (x - 1 > 0) {
			count += spiral[x - 1][y];
		}
		if (x - 1 > 0 && y + 1 < size - 1) {
			count += spiral[x - 1][y + 1];
		}
		if (y - 1 > 0) {
			count += spiral[x][y - 1];
		}
		if (y + 1 < size - 1) {
			count += spiral[x][y + 1];
		}
		if (x + 1 < size - 1 && y - 1 > 0) {
			count += spiral[x + 1][y - 1];
		}
		if (x + 1 < size - 1) {
			count += spiral[x + 1][y];
		}
		if (x + 1 < size - 1 && y + 1 < size - 1) {
			count += spiral[x + 1][y + 1];
		}

		return count;
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