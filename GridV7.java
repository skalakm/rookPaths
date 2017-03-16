package n3grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
 * Has both the row and col for the greater than rows and cols with loop and improved col and row cut offs
 */
public class GridV7 {
	private int grid[][];
	private int cFilled[];
	private int rFilled[];
	private int n;
	private final int col = 10;
	public ArrayList<String> paths;
	private static int MAX_SIZE;
	private static int eval = 0;

	public GridV7(int num) {
		n = num;
		grid = new int[n][col];
		cFilled = new int[n];
		rFilled = new int[col];
		paths = new ArrayList<String>();
	}

	public static void main(String args[]) throws FileNotFoundException {
		for (int i = 1; i < 6; i++) {
			eval = 0;
			GridV7 test = new GridV7(i);
			MAX_SIZE = test.n * test.col;
			// PrintWriter wr = new PrintWriter(new File("gridOutput" + test.n +
			// "Modfied2.txt"));
			PrintWriter wr = new PrintWriter(new File("gridOutput.txt"));
			test.initialize(wr);
			HashSet<Integer> explored = new HashSet<Integer>();
			ArrayList<Integer> path = new ArrayList<Integer>();
			explored.add(0);
			path.add(0);
			test.generatePath(0, 0, path, explored);
			System.out.println("\n\n" + i + ": Number of paths: " + test.paths.size());
			System.out.println("Number of evals: " + eval);
			// for (String str : test.paths) {
			// System.out.println(str);
			// }
			// System.out.close();
		}
	}

	private void initialize(PrintWriter wr) throws FileNotFoundException {
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

	private void generatePath(int i, int j, ArrayList<Integer> path, HashSet<Integer> explored) {
		eval++;
		// System.out.println(path + " Space: " + countSpace(path));
		ArrayList<Integer> moves = validCells(i, j);
		if ((path.size() == MAX_SIZE) && path.get(path.size() - 1) == (MAX_SIZE - col)) {
			paths.add(toString(path));
		} else if (path.size() >= MAX_SIZE || moves.size() == 0
				|| (path.size() != 0 && path.get(path.size() - 1) == (MAX_SIZE - col))) {
			return;
		} else {
			// System.out.println("Node: " + grid[i][j]);
			// System.out.print("Contents of explored: ");
			// System.out.println("Size of moves: " + moves.size());
			// for (Integer num : moves) {
			// System.out.print(" " + num + " ");
			// }
			// System.out.print("\n");
			for (Integer num : moves) {
				if ((!path.contains(num)) && !colFilled(num / col) && !rowFilled(num % col)) {
					path.add(num);
					explored.add(num);
					cFilled[num / col]++;
					rFilled[num % col]++;
					// System.out.println("Passing: " + (path + " &" + num));
					generatePath(num / col, num % col, path, explored);
					path.remove(num);
					explored.remove(num);
					cFilled[num / col]--;
					rFilled[num % col]--;
					// System.out.println(path);
				}
				// else {
				// System.out.println("Conatins: " + num);
				// }
				// paths.add(path);

			}
		}
	}

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

	private boolean colFilled(int j) {
		for (int i = n - 1; i > j; i--) {
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

	private boolean rowFilled(int i) {
		/*
		 * The
		 */
		for (int j = i; j >= 0; j--) {
			if (rFilled[j] >= n) {
				return true;
			}
		}
		boolean lesserFilled = false;
		for (int j = i - 1; j > 0; j--) {
			if (rFilled[j] >= n && !lesserFilled) {
				lesserFilled = true;
			} else if (rFilled[j] != n && lesserFilled) {
				return true;
			}
		}
		return false;
	}
}
