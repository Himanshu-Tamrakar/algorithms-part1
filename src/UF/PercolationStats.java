package UF;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDANCEE_95 = 1.96;
    private double[] thresholds;
    private int experiments;
    private int size;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0) throw new java.lang.IllegalArgumentException("N is out of bounds");
        if (t <= 0) throw new java.lang.IllegalArgumentException("T is out of bounds");

        size = n;
        experiments = t;
        thresholds = new double[experiments];

        for (int i = 0; i < t; i++) {
            thresholds[i] = findPercolationThreshold();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (experiments == 1) return Double.NaN;
        return StdStats.stddev(thresholds);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDANCEE_95*stddev()/Math.sqrt(experiments);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDANCEE_95*stddev()/Math.sqrt(experiments);
    }

    private double findPercolationThreshold() {
        Percolation perc = new Percolation(size);
        int i, j;
        int count = 0;
        while (!perc.percolates()) {
            do {
                i = StdRandom.uniformInt(size) + 1;
                j = StdRandom.uniformInt(size) + 1;
            } while (perc.isOpen(i, j));
            count++;
            perc.open(i, j);
        }
        return count/(Math.pow(size, 2));
    }

    // test client, described below
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
