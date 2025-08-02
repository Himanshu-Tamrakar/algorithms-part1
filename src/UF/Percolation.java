package UF;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private WeightedQuickUnionUF uf;
    private int topIndex;
    private int bottomIndex;
    private  int openSites = 0;
    public Percolation(int n) {
        topIndex = 0;
        bottomIndex = n * n + 1;
        sites = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);

    }

    private void validate(int row, int col) {
        int n = sites.length;
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException();
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    private int getIndex(int row, int col) {
        int len = sites.length;
        return len * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        sites[row-1][col-1] = true;
        openSites++;
        if (row == 1) {
            uf.union(getIndex(row, col), topIndex);
        }
        if (col == sites.length) {
            uf.union(getIndex(row, col), bottomIndex);
        }

        if (row > 1 && isOpen(row-1, col)) {
            uf.union(getIndex(row, col), getIndex(row-1, col));
        }

        if (row < sites.length && isOpen(row+1, col)) {
            uf.union(getIndex(row, col), getIndex(row+1, col));
        }

        if (col > 1 && isOpen(row, col-1)) {
            uf.union(getIndex(row, col), getIndex(row, col-1));
        }

        if (col < sites.length && isOpen(row, col+1)) {
            uf.union(getIndex(row, col), getIndex(row, col+1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
        return  uf.find(getIndex(row, col)) == uf.find(topIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topIndex) == uf.find(bottomIndex);
    }
    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        System.out.println(percolation.sites[0][0]);
    }
}
