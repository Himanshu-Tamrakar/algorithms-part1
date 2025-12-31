package DataCompression.BurrowsWheeler;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final int[] idx;
    private final int n;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        String[] suffixArray = new String[s.length()];
        idx = new int[suffixArray.length];
        n = s.length();

        // Circular Sufix
        for (int i = 0; i < s.length(); i++) {
            suffixArray[i] = s.substring(i) + s.substring(0, i);
            idx[i] = i;
        }

        // LSD sorting
        sort(suffixArray, s.length());
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i >= n) throw new IllegalArgumentException();
        return idx[i];
    }


    private void sort(String[] a, int w) {
        int n = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[n];
        int[] auxIdx = new int[n];

        for (int d = w-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < n; i++) {
                aux[count[a[i].charAt(d)]] = a[i];
                auxIdx[count[a[i].charAt(d)]++] = idx[i];
            }

            // copy back
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
                idx[i] = auxIdx[i];
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(circularSuffixArray.length());
        StdOut.println(circularSuffixArray.index(2));
    }
}
