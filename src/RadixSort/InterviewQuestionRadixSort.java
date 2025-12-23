package RadixSort;

import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.SuffixArray;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InterviewQuestionRadixSort {

    /**
     * Question 1
     * 2-sum. Given an array a of n 64-bit integers and a target value T, determine whether there are two distinct integers i and j such that i + j = T.
     * Your algorithm should run in linear time in the worst case.
     */
    public int[] twoSum(int[] a, int target) {
        LSD.sort(a); // Least significat digit first take linear time to sort string.

        int i = 0;
        int j = a.length-1;

        while (i < j) {
            int sum = a[i] + a[j];

            if (sum < target) i++;
            else if (sum > target) j--;
            else return new int[]{i, j};
        }

        return new int[] {-1, -1};
    }

    /**
     * Question 2
     * American flag sort. Given an array of n objects with integer keys between
     * 0 and R − 1. design a linear-time algorithm to rearrange them in ascending order. Use extra space at most proportional to $R$$.
     *
     * Solution: Solution to this problem is key index based sorting
     */
    public void americalFlagSorting(String[] names, Integer[] values) {
        // This is key index not americal flag. This is taking aux array O(n) space complexity.
        int R = 256;

        String[] auxNames = new String[names.length];
        Integer[] auxValues = new Integer[values.length];
        int[] count = new int[R+1];
        for (int i = 0; i < values.length; i++) {
            count[values[i]+1]++;
        }

        // commulative
        for (int i = 1; i < R; i++) {
            count[i] += count[i-1];
        }

        for (int i = 0; i < values.length; i++) {
            auxValues[count[values[i]]] = values[i];
            auxNames[count[values[i]]++] = names[i];
        }

        for (int i = 0; i < names.length; i++) {
            names[i] = auxNames[i];
            values[i] = auxValues[i];
        }
    }

    /**
     * Cyclic rotations. Two strings
     * s and
     * t are cyclic rotations of one another if they have the same length and s consists of a suffix of t followed by a prefix of t.
     * For example, "suffixsort" and "sortsuffix" are cyclic rotations.
     * Given n distinct strings, each of length L, design an algorithm to determine whether there exists a pair of distinct strings that are cyclic rotations of one another.
     * For example, the following list of n = 12, strings of length L = 10 contains exactly one pair of strings ("suffixsort" and "sortsuffix") that are cyclic rotations of one another.
     *
     */
    public boolean cyclicRotation(String[] inputs) {
        // Manber Mayer for suffixes whci is minimum rotation algoriths is required.
        return false;
    }

    private String[] getSuffixes(String input) {
        String[] suffixes = new String[input.length()];
        for (int i = 0; i < input.length(); i++) {
            suffixes[i] = input.substring(i);
        }
        return suffixes;
    }

    /**
     * Question 4: Context based search using suffix sort
     *
     */

    /**
     * Longest Common Repeated Substring using suffix sort
     * @param args
     */

    public static void main(String[] args) {
        InterviewQuestionRadixSort interviewQuestionRadixSort = new InterviewQuestionRadixSort();
        System.out.println("Interview Question 1");
        StringBuilder s = new StringBuilder();
        int[] input1 = new int[] {2, 7, 11, 15};
        int[] result1 = interviewQuestionRadixSort.twoSum(input1, 9);
        if (result1[0] != -1) {
            s.append("Target 9: ");
            s.append(input1[result1[0]] + "(" + result1[0] + ")");
            s.append(" + ");
            s.append(input1[result1[1]] + "(" + result1[1] + ")");
        }
        System.out.println(s);

        String[] flags = new String[]{"flag1", "flag2", "flag3", "flag4", "flag5"};
        Integer[] values = new Integer[] {3, 1, 2, 4, 2};

        System.out.println("Interview Question 2");

        interviewQuestionRadixSort.americalFlagSorting(flags, values);
        for (int i = 0; i < flags.length; i++) {
            System.out.println(flags[i] + " : " + values[i]);
        }



    }

}
