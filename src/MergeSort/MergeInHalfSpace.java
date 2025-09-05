/**
 * Question 1 Merging with smaller auxiliary array.
 * Suppose that the subarray a [ 0 ] a[0]a, open bracket, 0, close bracket to a [ n − 1 ] a[n−1]a,
 * open bracket, n, minus, 1, close bracket is sorted and the subarray a [ n ] a[n]a, open bracket, n, close bracket to a [ 2 ∗ n − 1 ] a[2∗n−1]a,
 * open bracket, 2, times, n, minus, 1, close bracket is sorted. How can you merge the two subarrays so that a [ 0 ] a[0]a, open bracket, 0,
 * close bracket to a [ 2 ∗ n − 1 ] a[2∗n−1]a, open bracket, 2, times, n, minus, 1, close bracket is sorted using an auxiliary array of length n nn (
 * instead of 2 n 2n2, n)?
 */
package MergeSort;

import edu.princeton.cs.algs4.StdOut;

public class MergeInHalfSpace {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = 0; k <= mid; k++) {
            aux[k] = a[k];
        }

        int i = 0;
        int j = mid+1;
        for (int k = 0; k <= hi; k++) {
            if (i > mid) a[k] = a[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(a[j], aux[i])) a[k] = a[j++];
            else a[k] = aux[i++];
        }
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int v, int w) {
        Comparable temp = a[v];
        a[v] = a[w];
        a[w] = temp;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[] {1,4,7,5,6,8};
//        Integer[] a = new Integer[] {1,8,9,5,6,8};
        Integer[] aux = new Integer[3];
        merge(a, aux, 0, 2, 5);
        show(a);
    }


}
