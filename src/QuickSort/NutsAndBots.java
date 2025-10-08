/**
 * Nuts and bolts. A disorganized carpenter has a mixed pile of n nn nuts and n nn bolts.
 * The goal is to find the corresponding pairs of nuts and bolts.
 * Each nut fits exactly one bolt and each bolt fits exactly one nut.
 * By fitting a nut and a bolt together, the carpenter can see which one is bigger
 * (but the carpenter cannot compare two nuts or two bolts directly). Design an algorithm
 * for the problem that uses at most proportional to n log ⁡ n nlognn, log, n compares (probabilistically).
 */
package QuickSort;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class NutsAndBots {
    private static int partition(Comparable[] a, int lo, int hi, Comparable v) {
        for (int i = lo; i <= hi; i++) {
            if (a[i].compareTo(v) == 0) {
                exch(a, i, lo);
                break;
            }
        }

        int i = lo, j = hi+1;
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) break;
            }

            while (less(v, a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void matchPairs(Comparable[] nuts, Comparable[] bolts, int lo, int hi) {
        if (lo >= hi) return;

        // partition bolts with nut[lo]
        int pivotBoltIndex = partition(bolts, lo, hi, nuts[lo]);

        // partition nuts with bolt pivot
        int pivotNutIndex = partition(nuts, lo, hi, bolts[pivotBoltIndex]);

        // recurse on both sides
        matchPairs(nuts, bolts, lo, pivotNutIndex - 1);
        matchPairs(nuts, bolts, pivotNutIndex + 1, hi);
    }

    public static void organized(Comparable[] nuts, Comparable[] bolts) {
        if (nuts.length != bolts.length)
            throw new IllegalArgumentException("nuts and bolts must be same length");
        StdRandom.shuffle(nuts);
        StdRandom.shuffle(bolts);
        matchPairs(nuts, bolts, 0, nuts.length - 1);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void show(Comparable[] nuts, Comparable[] bolts) {
        for (int i = 0; i < nuts.length; i++) {
            StdOut.println("Nut: " + nuts[i] + " , Bots: " + bolts[i]);
        }
    }


    public static void main(String[] args) {
        Comparable[] nuts = new Comparable[]{1,2,3,4,5,6,7,8,9};
        Comparable[] bots = new Comparable[]{1,2,3,4,5,6,7,8,9};

        StdRandom.shuffle(nuts);
        StdRandom.shuffle(bots);

        organized(nuts, bots);

        show(nuts, bots);

    }
}
