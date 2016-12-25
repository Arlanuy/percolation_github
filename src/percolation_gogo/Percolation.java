import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {
	boolean[][] n_square;
	static int[] rows_array;
	static int[] columns_array;
	int n;	
	static WeightedQuickUnionUF wquf;
	final boolean OPEN = true;
	final boolean CLOSE = false;
	int top_node;
	int bottom_node;
	
	public Percolation(int n) {
		this.n = n;
		top_node = (n * n);
		bottom_node = (n * n) + 1;
		n_square = new boolean[n][n];
		wquf = new WeightedQuickUnionUF((n * n) + 2);
		initNSquare();
		int i = 0, row = 1, col = 1;
		for (int num: rows_array) {
			StdOut.println("row: " + rows_array[i] + " col: " + columns_array[i]);
			open(rows_array[i], columns_array[i]);
			i++;
		}
		
	}  // create n-by-n grid, with all sites blocked
	
	
	private void initNSquare() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				n_square[i - 1][j - 1] = CLOSE;
				if (i == 1) {
					wquf.union(xyTo1D(i, j), bottom_node);
				}
			}
		}
	}
	
	private boolean checkValidIndex(int row, int col) {
		if ((row > n || col > n)|| (row < 1 || col < 1)) {					
			return false;
		}
		return true;

	}
	
	private int xyTo1D(int row, int col) {
		return (((row - 1) * n) + (col - 1));		
	}
	
	public void open(int row, int col) {
		if (checkValidIndex(row, col) == false) {
			throw new IndexOutOfBoundsException("row index i out of bounds");
		}
		
		else {
			n_square[row - 1][col- 1] = OPEN;
			wquf.union(xyTo1D(row, col), top_node);
			StdOut.println("Opened " + xyTo1D(row, col));
			if (((row - 1) >= 0) &&
			(wquf.connected(row, row - 1) == false)) {
				wquf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
			}
			
			if (((row + 1) < n) &&
				(wquf.connected(row, row + 1) == false)) {
					wquf.union(xyTo1D(row,col), xyTo1D(row + 1, col));
			}
			
			if (((col - 1) >= 0) &&
				(wquf.connected(col, col - 1) == false)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				}
				
			if (((col + 1) < n) &&
				(wquf.connected(col, col + 1) == false)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
			}
		}
	
	}       // open site (row, col) if it is not open already 
	/**    
	public boolean isOpen(int row, int col){
		
	}  // is site (row, col) open?
 
	public boolean isFull(int row, int col) {
		
	} // is site (row, col) full?
	   
	public boolean percolates() {
		
	}             // does the system percolate?
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.print("What is the value for n: ");
		int N = StdIn.readInt();
		 rows_array = new int[1];
		 columns_array = new  int[1];
		 int i = 1;
		 while (!StdIn.isEmpty())
		 {
			 rows_array = Arrays.copyOf(rows_array, i);
			 columns_array = Arrays.copyOf(columns_array, i);
			 int p = StdIn.readInt();
			 int q = StdIn.readInt();
			 rows_array[i - 1] = p;
			 columns_array[i - 1] = q;
			 i++;
		 }
		 Percolation perc = new Percolation(N);
	}

}