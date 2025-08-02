package UF;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] size;
    private int count;

    public WeightedQuickUnionUF(int n) {
        count = n;
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        int rootP = root(p);
        int rootQ = root(q);

        if (rootP == rootQ) return;

        if (size[rootP] > size[rootQ]) {
            id[rootQ] = rootP;
            size[rootP] += size[rootQ];
        } else {
            id[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }

        count--;
    }

    public int find(int p) {
        validate(p);
        return root(p);
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    public int count() {
        return count;
    }

    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public static void main(String[] args) {
//        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/tinyUF.txt");
//        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/mediumUF.txt");
        fileTest("/home/decimal/personal/algorithms/algorithms/src/UF/largeUF.txt");

//        int n = StdIn.readInt();
//        QuickFindUF uf = new QuickFindUF(n);
//        while (!StdIn.isEmpty()) {
//            int p = StdIn.readInt();
//            int q = StdIn.readInt();
//            if (uf.find(p) == uf.find(q)) continue;
//            uf.union(p, q);
//            StdOut.println(p + " " + q);
//        }
//        StdOut.println(uf.count() + " components");


    }

    public static void fileTest(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int n = scanner.nextInt();

            WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);

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
