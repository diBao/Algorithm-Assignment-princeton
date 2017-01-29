/*
 * Compilation: javac-algs4 PercolationStats.java
 * Execution: java-algs4 PercolationStats 200 100
 * Dependencies: Percolation.java
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    //private Percolation pl;
    private double[] threshold;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0){
            throw new IllegalArgumentException(Integer.toString(n));
        }
        if(trials <= 0){
            throw new IllegalArgumentException(Integer.toString(trials));
        }

        int openCount, row, col;
        threshold = new double[trials];

        for(int i = 0; i < trials;i++){
            openCount = 0;
            Percolation pl = new Percolation(n);
            while(!pl.percolates()){
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
                if(pl.isOpen(row, col))
                  continue;
                pl.open(row, col);
                openCount++;
            }
            threshold[i] =  (double) openCount / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return (mean() - 1.96*stddev() / Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return (mean() + 1.96*stddev() / Math.sqrt(threshold.length));
    }

    // test client (described below)
    public static void main(String[] args){
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]),
                               Integer.parseInt(args[1]));
        System.out.println("mean                    = " + pls.mean());
        System.out.println("stddev                  = " + pls.stddev());
        System.out.println("95% confidence Interval = [" + pls.confidenceLo() +
                           ", " + pls.confidenceHi() + "]");
    }
}
