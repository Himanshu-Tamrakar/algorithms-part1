/*
   Question 2
   Permutation.
   Given two integer arrays of size N, design a subquadratic algorithm to determine whether one is a permutation of the other.
   That is, do they contain exactly the same entries but, possibly, in a different order.
 */
package ElementrySort;

import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static boolean isPermutation(Integer[] a, Integer[] b) {
        if (a.length != b.length) return false;
        Shell.sort(a);
        Shell.sort(b);

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Integer[] a = new Integer[n];
        Integer[] b = new Integer[n];
        StdOut.println("Enter " + n + "Items for first array");
        for (int i = 0; i < n; i++) {
            a[i] = StdIn.readInt();
        }
        StdOut.println("Enter " + n + "Items for second array");
        for (int i = 0; i < n; i++) {
            b[i] = StdIn.readInt();
        }

        StdOut.println("Is Permutation: " + Permutation.isPermutation(a, b));
    }
}
