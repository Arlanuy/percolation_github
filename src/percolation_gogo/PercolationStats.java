import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats{
	private int n;
	private int trials;
	private double mean, s_dev;
	private double[] a;
	private static int when_seed = 4;
	
	private Percolation p;
	
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	{
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		this.trials = trials;
		p = new Percolation(n);
	}
	
	private double percTresh() {
		while (!p.percolates()) {
			int random_num = StdRandom.uniform(n) + 1; //+ 1 because this could return zero
			int random_num2 = StdRandom.uniform(n) + 1;
			p.open(random_num, random_num2);
		}
		double open_sites = p.numberOfOpenSites();

		return open_sites;
	}
	
	public double mean()                          // sample mean of percolation threshold
	{
		a = new double[trials];
		for (int i = 0; i < trials; i++) {
			StdRandom.setSeed(i+1);
			a[i] = percTresh()/(n*n);
			if (i != (trials - 1)) {
				p = new Percolation(n);
			}
		}
		mean = StdStats.mean(a);
		return mean;
		
	}
	
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		s_dev = StdStats.stddev(a);
		return s_dev;
	}
	
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		double S_error = s_dev/Math.sqrt(trials);
		double M_error = S_error * 2;
		double C_interval = mean - M_error;
		return C_interval;
	}
	
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		double S_error = s_dev/Math.sqrt(trials);
		double M_error = S_error * 2;
		double C_interval = mean + M_error;
		return C_interval;
	}

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
