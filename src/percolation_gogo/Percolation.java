import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {
	boolean[][] n_square;
	int n;	
	WeightedQuickUnionUF wquf;
	final boolean OPEN = true;
	final boolean CLOSE = false;
	int top_node;
	int bottom_node;
	double num_open_sites = 0;
	
	public Percolation(int n) {
		this.n = n;
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		top_node = (n * n);
		bottom_node = (n * n) + 1;
		n_square = new boolean[n][n];
		wquf = new WeightedQuickUnionUF((n * n) + 2);
		initNSquare();
	}  // create n-by-n grid, with all sites blocked
	
	public double getNumOpenSites() {
		return num_open_sites;
	}
	
	private void initNSquare() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				n_square[i][j] = CLOSE;
				if (i == n - 1) {
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
		return ((row * n) + col);		
	}
	
	public void open(int row, int col) {
		if (checkValidIndex(row, col) == false) {
			throw new IndexOutOfBoundsException("row index i out of bounds");
		}
		
		else {
			row--;
			col--;
			n_square[row][col] = OPEN;
			if (row == 0) {
				wquf.union(xyTo1D(row, col), top_node);
				num_open_sites++;
			}
			
			if (((row - 1) >= 0) && (n_square[row-1][col] == OPEN) &&
			(wquf.connected(xyTo1D(row, col), xyTo1D((row - 1), col)) == false)) {
				wquf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				num_open_sites++;
			}
			
			else if (((row + 1) < n) && (n_square[row+1][col] == OPEN) &&
				(wquf.connected(xyTo1D(row, col), xyTo1D((row + 1), col)) == false)) {
					wquf.union(xyTo1D(row,col), xyTo1D(row + 1, col));
					num_open_sites++;
			}
			
			if (((col - 1) >= 0) && (n_square[row][col - 1] == OPEN) &&
				(wquf.connected(xyTo1D(row, col), xyTo1D(row, col - 1)) == false)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
					num_open_sites++;
			}
				
			else if (((col + 1) < n) && (n_square[row][col + 1] == OPEN) &&
				(wquf.connected(xyTo1D(row, col), xyTo1D(row, col + 1)) == false)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
					num_open_sites++;
			}
		}
	
	}       // open site (row, col) if it is not open already 
	
	
	public boolean isOpen(int row, int col){
		return n_square[row - 1][col - 1];
	}  // is site (row, col) open?
 
	public boolean isFull(int row, int col) {
		if (n_square[row - 1][col - 1] == OPEN) {
			return false;
		}
		
		else {
			return true;
		}
	} // is site (row, col) full?
	   
	public boolean percolates() {
		//int random_start_column = 1;
		if (wquf.connected(top_node, bottom_node)) {
			return true;
		}
		return false;
	}             // does the system percolate?

}