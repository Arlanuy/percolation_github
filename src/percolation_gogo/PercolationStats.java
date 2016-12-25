import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class   PercolationStats{
	int n;
	int trials;
	double mean;
	
	Percolation p;
	
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	{
		this.n = n;
		this.trials = trials;
		p = new Percolation(n);
		StdRandom.setSeed(4);
		StdOut.println("The seed is " + StdRandom.getSeed());

		
	}
	
	private int percTresh() {
		while (!p.percolates()) {
			int random_num = StdRandom.uniform(n*n);
			int random_num2 = StdRandom.uniform(n*n);
			StdOut.println(" and the rd is " + random_num + " " + random_num2);
			p.open(random_num, random_num2);			
		}
		return p.getNumOpenSites();
	}
	
	public double mean()                          // sample mean of percolation threshold
	{
		int[] a = new int[n];
		for (int i = 0; i < trials; i++) {
			a[i] = percTresh();
		}
		mean = StdStats.mean(a);
		StdOut.println("mean is " + mean);
		return mean;
		
	}
	/**
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		
	}
	
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		
	}
	
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		
	}
	*/
   public static void main(String[] args)        // test client (described below)
   {
	   int N = Integer.parseInt(args[0]);
	   int T = Integer.parseInt(args[1]);
	   if (N <= 0 || T <= 0) {
		   throw new IllegalArgumentException();
	   }
	   PercolationStats ps = new PercolationStats(N, T);
   }
}
