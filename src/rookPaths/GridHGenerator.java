

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/*
* Has both the row and col for the greater than rows and cols with loop and improved col and row cut offs
*/
public class GridHGenerator {
	private int grid[][];
	private int cFilled[];
	private int rFilled[];
	private final int row = 10;
	private final int col = 10;
	private final int depth = 20;
	private static ArrayList<Integer> path;
	private static int MAX_SIZE;
	private static HashSet<Integer> explored;

	public GridHGenerator() {
		grid = new int[row][col];
		cFilled = new int[row];
		rFilled = new int[col];
		path = new ArrayList<Integer>();
		explored = new HashSet<Integer>();
	}

	public static void main(String args[]) throws FileNotFoundException {
		// the input format is row, col, path
		GridHGenerator test = new GridHGenerator();
		PrintWriter writer = new PrintWriter("generatedPaths.txt");
		MAX_SIZE = test.row * test.col;
		test.initialize();
		explored.add(0);
		path.add(0);
		test.generatePath(0, 0, path, explored, writer);
		writer.close();
	}

	private void initialize() {
		for (int i = 0; i < row * col; i++) {
			grid[i / col][i % col] = i;
		}

		for (int i = col - 1; i >= 0; i--) {
			for (int j = 0; j < row; j++) {
				System.out.print(grid[j][i] + "\t");
			}
			System.out.println();
		}

	}

	private void generatePath(int i, int j, ArrayList<Integer> path, HashSet<Integer> explored, PrintWriter writer) {
		ArrayList<Integer> moves = validCells(i, j);
		if (path.size() == depth) {
			String data = row + " " + col + " " + pathGenerator(path);
			System.out.println(data);
			writer.println(data);
		} else if (path.size() >= depth || moves.size() == 0
				|| (path.size() != 0 && path.get(path.size() - 1) == (MAX_SIZE - col))) {
			return;
		} else {
			for (Integer num : moves) {
				if ((!explored.contains(num)) && !colFilled(num / col) && !rowFilled(num % col)) {
					path.add(num);
					explored.add(num);
					cFilled[num / col]++;
					rFilled[num % col]++;
					generatePath(num / col, num % col, path, explored, writer);
					path.remove(num);
					explored.remove(num);
					cFilled[num / col]--;
					rFilled[num % col]--;
				}
			}
		}
	}

	private String pathGenerator(ArrayList<Integer> path2) {
		String path = "";
		for (int i = 0; i < path2.size(); i++) {
			path = path + path2.get(i) + ",";
		}
		return path;
	}

	private ArrayList<Integer> validCells(int i, int j) {
		ArrayList<Integer> sol = new ArrayList<Integer>();
		// up
		if (i - 1 >= 0) {
			sol.add(grid[i - 1][j]);
		}
		// down
		if (i + 1 != row) {
			sol.add(grid[i + 1][j]);
		}
		// right
		if (j + 1 != col) {
			// System.out.println(i + "" + j);
			sol.add(grid[i][j + 1]);
		}
		// left
		if (j - 1 >= 0) {
			sol.add(grid[i][j - 1]);
		}
		return sol;

	}

	public String toString(ArrayList<Integer> arr) {
		String str = "";
		for (int i = 0; i < arr.size(); i++) {
			str = str + arr.get(i) + " ";
		}
		return str;
	}

	/**
	 * The function checking if any column greater or less that itself has been
	 * completely filled.
	 * 
	 * @param j
	 *            Column number
	 * @return true if filled, false otherwise
	 */
	private boolean colFilled(int j) {
		for (int i = row - 1; i > j; i--) {
			if (cFilled[i] >= col) {
				return true;
			}
		}
		boolean lesserFilled = false;
		for (int i = j - 1; i > 0; i--) {
			if (cFilled[i] >= col && !lesserFilled) {
				lesserFilled = true;// a column was filled
			} else if (cFilled[i] != col && lesserFilled) {
				return true;// something was filled but this has not been filled
			}
		}
		return false;
	}

	/**
	 * The function checking if any row greater or less that itself has been
	 * completely filled.
	 * 
	 * @param i
	 *            Row number
	 * @return true if filled, false otherwise
	 */
	private boolean rowFilled(int i) {
		for (int j = i; j >= 0; j--) {
			if (rFilled[j] >= row) {
				return true;
			}
		}
		boolean lesserFilled = false;
		for (int j = i - 1; j > 0; j--) {
			if (rFilled[j] >= row && !lesserFilled) {
				lesserFilled = true;
			} else if (rFilled[j] != row && lesserFilled) {
				return true;
			}
		}
		return false;
	}
}
