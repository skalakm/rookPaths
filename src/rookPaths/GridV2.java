package rookPaths;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The second implementation to solve the SAW grid problem
 * 
 * @author Ashir Borah
 *
 */
public class GridV2 {
	private int grid[][];
	private int n;
	private final int col = 10;
	public ArrayList<String> paths;
	private static int MAX_SIZE;
	private static long eval = 0;

	public GridV2(int num) {
		n = num;
		grid = new int[n][col];
		paths = new ArrayList<String>();
	}

	public static void main(String args[]) {
		for (int i = 1; i < 10; i++) {
			long startTime = System.currentTimeMillis();
			GridV2 test = new GridV2(i);
			MAX_SIZE = test.n * test.col;
			test.initialize();
			HashSet<Integer> explored = new HashSet<Integer>();
			ArrayList<Integer> path = new ArrayList<Integer>();
			explored.add(0);
			path.add(0);
			test.generatePath(0, 0, path, explored);
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
	 * 
	 * @param i
	 *            the row number of the lattice point
	 * @param j
	 *            the column number of the lattice point
	 * @param path
	 *            the path generated to this point
	 * @param explored
	 *            a set of the nodes already explored in this path
	 */
	private void generatePath(int i, int j, ArrayList<Integer> path, HashSet<Integer> explored) {
		eval++;
		ArrayList<Integer> moves = validCells(i, j);
		if ((path.size() == MAX_SIZE) && path.get(path.size() - 1) == (MAX_SIZE - col)) {
			paths.add(toString(path));
		} else if (path.size() >= MAX_SIZE || moves.size() == 0
				|| (path.size() != 0 && path.get(path.size() - 1) == (MAX_SIZE - col))) {
			return;
		} else {
			for (Integer num : moves) {
				if ((!path.contains(num))) {
					path.add(num);
					explored.add(num);

					generatePath(num / col, num % col, path, explored);
					path.remove(num);
					explored.remove(num);
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
	 * Custom toString method
	 * 
	 * @param arr
	 *            the input array
	 * @return the string representation of the array
	 */
	public String toString(ArrayList<Integer> arr) {
		String str = "";
		for (int i = 0; i < arr.size(); i++) {
			str = str + arr.get(i) + " ";
		}
		return str;
	}
}
