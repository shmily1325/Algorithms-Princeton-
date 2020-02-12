/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final double record[];
    private final double CONFIDENCE_95=1.96;
    private final double m;
    private final double s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if ( n<=0 || trials <=0){
            throw new IllegalArgumentException("Illegal argument");
        }
        record = new double[trials];

        for (int i=0; i<trials; i++){
            int step=0;
            Percolation perc = new Percolation(n);
            while (perc.percolates()==false) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                perc.open(row, col);
                step++;
            }
            record[i] = ((double)perc.numberOfOpenSites()/(n*n));
        }

        m = StdStats.mean(record);
        s = StdStats.stddev(record);

    }

    // sample mean of percolation threshold
    public double mean(){
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return m-(CONFIDENCE_95*s)/Math.sqrt(record.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return m+(CONFIDENCE_95*s)/Math.sqrt(record.length);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats m = new PercolationStats(n, T);
        System.out.println("mean = " + m.mean());
        System.out.println("stddev = " + m.stddev());
        System.out.println("95% confidence interval = [" + m.confidenceLo() + ", " + m.confidenceHi() + "]");

    }

}