package UF;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickUnionUF {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components

    public QuickUnionUF(int n) {
        parent = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }


    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }


    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }


    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        count--;
    }

    public static void main(String[] args) {
//        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/tinyUF.txt");
//        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/mediumUF.txt");
//        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/largeUF.txt");

        int n = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

    public static void fileTest(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int n = scanner.nextInt();

            QuickUnionUF uf = new QuickUnionUF(n);

            for (int i = 0; i < n; i++) {
                int p = scanner.nextInt();
                int q = scanner.nextInt();

                if (uf.find(p) == uf.find(q)) continue;
                uf.union(p, q);
                System.out.println(p + " " + q);
            }

            System.out.println(uf.count() + " components");
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }


}
