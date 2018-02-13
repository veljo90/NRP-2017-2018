package KlasicniAlgoritam;

public class QueensProblem {
	
	public static final int BOARD_SIZE = 15;

	private static int[] b = new int[BOARD_SIZE];

	static boolean unsafe(int y) {
		int x = b[y];
		for (int i = 1; i <= y; i++) {
			int t = b[y - i];
			if (t == x || t == x - i || t == x + i) {
				return true;
			}
		}

		return false;
	}

	public static void putboard() {
		System.out.println("\n\nRješenje:");
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				System.out.print((b[y] == x) ? "|Q" : "|_");
			}
			System.out.println("|");
		}
	}

	public static void main(String[] args) {
		int y = 0;
		b[0] = -1;
		
		long startTime = System.currentTimeMillis();
		
		while (y >= 0) {
			do {
				b[y]++;
			} while ((b[y] < BOARD_SIZE) && unsafe(y));
			if (b[y] < BOARD_SIZE) {
				if (y < BOARD_SIZE - 1) {
					b[++y] = -1;
				} else {
					putboard();
					break;
				}
			} else {
				y--;
			}
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("\nUkupno vrijeme trajanja : " + (((double) endTime - startTime) / 1000) + " sekundi");
	}
}
