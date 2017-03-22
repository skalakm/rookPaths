package rookPaths;

import java.util.ArrayList;

/**
 * The first implementation to solve the SAW grid problem
 * 
 * @author: Ashir Borah
 */
public class GridV1 {
	private int grid[][];
	private int n;
	private final static int col = 10;
	public ArrayList<String> paths;

	private static long eval = 0;

	public GridV1(int num) {
		n = num;
		grid = new int[n][col];
		paths = new ArrayList<String>();
	}

	public static void main(String args[]) {
		for (int i = 1; i < 10; i++) {
			long startTime = System.currentTimeMillis();
			GridV1 test = new GridV1(i);
			test.initialize();
			test.generatePath(0, 0, " 0 ");
			long endTime = System.currentTimeMillis();
			System.out.println(eval + " \t" + (double) (endTime - startTime) / 1000);
		}

	}

	/**
	 * Initializes the grid
	 */
	private void initialize() {
		for (int i = 0; i < n * col; i++) {
			grid[i / col][i % col] = i;
		}
		for (int i = col - 1; i >= 0; i--) {
			for (int j = 0; j < n; j++) {
				System.out.print(grid[j][i] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Generates the required path
	 * 
	 * @param i
	 *            the row number of the lattice point
	 * @param j
	 *            the column number of the lattice point
	 * @param path
	 *            the path generated to this point
	 */
	private void generatePath(int i, int j, String path) {
		eval++;
		ArrayList<Integer> moves = validCells(i, j);
		if ((countSpace(path) == n * col + 1) && path.endsWith(((n - 1) * col) + " ")) {
			// checks if the number of spaces is equal to the total number of
			// points as each point adds a space.
			paths.add(path);
		} else if ((countSpace(path) >= n * col + 1) || moves.size() == 0 || path.endsWith(((n - 1) * col) + " ")) {
			return;
		} else {
			for (Integer num : moves) {
				if ((!path.contains(" " + num.toString() + " "))) {
					generatePath(num / col, num % col, path + num + " ");
				}
			}
		}
	}

	/**
	 * Method to generate the valid points from a given point
	 * 
	 * @param i
	 *            the row number of the lattice point
	 * @param j
	 *            the column number of the lattice point
	 * @return the valid points from the given point without taking into
	 *         consideration which points have been already visited
	 */
	private ArrayList<Integer> validCells(int i, int j) {
		ArrayList<Integer> sol = new ArrayList<Integer>();
		// up
		if (i - 1 >= 0) {
			sol.add(grid[i - 1][j]);
		}
		// down
		if (i + 1 != n) {
			sol.add(grid[i + 1][j]);
		}
		// right
		if (j + 1 != col) {
			sol.add(grid[i][j + 1]);
		}
		// left
		if (j - 1 >= 0) {
			sol.add(grid[i][j - 1]);
		}
		return sol;

	}

	/**
	 * A method to count the number of spaces in a method
	 * 
	 * @param str
	 *            the input string
	 * @return number of spaces in the string
	 */
	private int countSpace(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				count++;
			}
		}
		return count;
	}
}
