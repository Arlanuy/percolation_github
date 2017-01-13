import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] n_square_open;
	private int n;	
	private WeightedQuickUnionUF wquf;
	private boolean is_full = false;
	private boolean is_open = false;
	private int top_node;
	private int bottom_node;
	private int num_open_sites = 0;
	
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		top_node = (n * n);
		bottom_node = (n * n) + 1;
		n_square_open = new boolean[n][n];
		wquf = new WeightedQuickUnionUF((n * n) + 2);
		num_open_sites = 0;
		initNSquare();
	}  // create n-by-n grid, with all sites blocked
	
	public int numberOfOpenSites() {
		return num_open_sites;
	}
	
	private void initNSquare() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == n - 1) {
					wquf.union(xyTo1D(i, j), bottom_node);
				}
				
				else if (i == 0) {
					wquf.union(xyTo1D(i, j), top_node);
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
			n_square_open[row][col] = true;
			num_open_sites++;
			

			
			if (((row - 1) >= 0) && (n_square_open[row - 1][col] == true)) {
				if (wquf.connected(xyTo1D(row, col), xyTo1D((row - 1), col)) == false) {
					if (row == (n-1))  {
						if (wquf.connected(xyTo1D(row - 1, col), top_node) == true){
							wquf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
						}						
						
					}
					
					else {
						wquf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
					}

				}	
			}
			
			if (((row + 1) < n) && (n_square_open[row + 1][col] == true)) {
				if (wquf.connected(xyTo1D(row, col), xyTo1D((row + 1), col)) == false) {
					wquf.union(xyTo1D(row,col), xyTo1D(row + 1, col));
				}
			}
			
			if (((col - 1) >= 0) && (n_square_open[row][col - 1] == true)) {
				if (wquf.connected(xyTo1D(row, col), xyTo1D(row, col - 1)) == false){
					wquf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				}
				else if (row == (n-1)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
					
				}
			}
				
			if (((col + 1) < n) && (n_square_open[row][col + 1] == true)) {			
				if (wquf.connected(xyTo1D(row, col), xyTo1D(row, col + 1)) == false) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				}
				
				else if (row == (n-1)) {
					wquf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
					
				}
			}
			
		}
	
	}       // open site (row, col) if it is not open already 
	
	
	public boolean isFull(int row, int col){
		if (row == 1){
			if (n_square_open[row - 1][col - 1]) {
				return true;
			}
			
			else {
				return false;
			}
		}
		
		else if (row == n) {
			if (n_square_open[row - 1][col - 1]) {
				if (wquf.connected(xyTo1D(row - 1, col-1), top_node)
					&& (wquf.find(xyTo1D(row- 1, col - 1)) != bottom_node)) {
					return true;
				}
				
				else {
					return false;
				}
			}
			
			else {
				return false;
			}
		}
		
		else if  (n_square_open[row - 1][col - 1]){
			return wquf.connected(xyTo1D(row - 1, col - 1), top_node);
		}
		
		else {
			return false;
		}
	}  // is site (row, col) full?
 
	public boolean isOpen(int row, int col) {
		return n_square_open[row - 1][col - 1];
	} // is site (row, col) open?
	   
	public boolean percolates() {
		//int random_start_column = 1;
		if (wquf.connected(top_node, bottom_node)) {
			return true;
		}
		return false;
	}             // does the system percolate?

}