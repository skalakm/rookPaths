package rookPaths;

import java.util.ArrayList;
import java.util.HashSet;

/*
* Has both the row and col for the greater than rows and cols with loop and improved col and row cut offs
*/
public class GridH1 {
	private int grid[][];
	private int cFilled[];
	private int rFilled[];
	private int row;
	private int col;
	public long valid_paths; // This will have to change to BigInteger
	public static ArrayList<Integer> path;
	private static int MAX_SIZE;
	private long eval = 0;
	private static HashSet<Integer> explored;
	private boolean overflow = false;

	public GridH1() {
	}

	public void init(int num, int num1) {
		/*
		 * row = num; col = num1; grid = new int[row][col]; cFilled = new
		 * int[row]; rFilled = new int[col]; eval = 0;
		 */
	}

	public static void main(String args[]) {
		// the input format is row, col, path
		GridH1 test = new GridH1();
		test.getData(args);
		test.init(test.row, test.col);
		MAX_SIZE = test.row * test.col;
		test.initialize();
		// explored.add(0);
		// paths.add(0);

		// System.out.println(path.get(path.size() - 1) / test.col);
		// System.out.println(path.get(path.size() - 1) % test.col);
		// System.out
		// .println("val" + test.grid[path.get(path.size() - 1) / test.col][path.get(path.size() - 1) % test.col]);

		test.generatePath(path.get(path.size() - 1) / test.col, path.get(path.size() - 1) % test.col, path, explored);
		System.out.println(test.row + ", " + test.col + ", " + test.eval + ", " + test.valid_paths + ", "
				+ test.overflow + ", " + args[2]);
	}

	private void getData(String args[]) {
		// String[] tokens = args[0].split(",");
		row = Integer.parseInt(args[0]);
		col = Integer.parseInt(args[1]);
		grid = new int[row][col];
		cFilled = new int[row];
		rFilled = new int[col];
		eval = 0;
		String[] input_paths = args[2].split(",");
		explored = new HashSet<Integer>();
		path = new ArrayList<Integer>();
		for (int i = 0; i < input_paths.length; i++) {
			int num = Integer.parseInt(input_paths[i]);
			path.add(num);
			explored.add(num);
			cFilled[num / col]++;
			rFilled[num % col]++;
		}
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

	private void generatePath(int i, int j, ArrayList<Integer> path, HashSet<Integer> explored) {
		eval++;

		ArrayList<Integer> moves = validCells(i, j);
		/*
		 * if (eval < 100) { System.out.println(i + " " + j + " " + grid[i][j] +
		 * moves.toString() + "  Path: " + path.toString()); } if (path.size()
		 * >= 15) { // System.out.println(path + " Outside" + path.size() +
		 * " moves: " + // moves.toString()); }
		 */
		if ((path.size() == MAX_SIZE) && path.get(path.size() - 1) == (MAX_SIZE - col)) {
			valid_paths++;
			System.out.println(path);
			if (valid_paths < 0) {
				overflow = true;
			}
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
				if ((!explored.contains(num)) && !colFilled(num / col) && !rowFilled(num % col)) {
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
