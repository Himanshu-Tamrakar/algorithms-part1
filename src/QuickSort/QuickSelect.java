package QuickSort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSelect {

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) break;
            }

            while (less(v, a[--j])) {
                if (hi == lo) break;
            }

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int v, int w) {
        Comparable temp = a[v];
        a[v] = a[w];
        a[w] = temp;
    }

    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length-1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k)      lo = j+1;
            else if (j > k) hi = j-1;
            else            return a[j];
        }
        return a[lo];
    }

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/QuickSort/tiny.txt");
        Comparable[] a = in.readAllStrings();
        Comparable item = select(a, 4);
        StdOut.println(item);
    }
}
