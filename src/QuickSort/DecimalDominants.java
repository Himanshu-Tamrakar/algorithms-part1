/**
 * Decimal dominants. Given an array with n nn keys, design an algorithm to find all values that occur more than n / 10
 * 10 times. The expected running time of your algorithm should be linear.
 */
package QuickSort;

import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class DecimalDominants {
    private Set<Integer> result = new HashSet<>();
    public DecimalDominants(int[] keys, int k) {
        HashMap<Integer, Integer> candidate = new HashMap<>();
        for (int x: keys) {
            if (candidate.containsKey(x)) candidate.put(x, candidate.get(x) + 1);
            else if (candidate.size() < k-1) candidate.put(x, 1);
            else {
                List<Integer> toRemove = new ArrayList<>();
                for (int key : candidate.keySet()) {
                    candidate.put(key, candidate.get(key) - 1);
                    if (candidate.get(key) == 0) toRemove.add(key);
                }
                for (int rem : toRemove) candidate.remove(rem);

            }
        }

        Map<Integer, Integer> count = new HashMap<>();
        for (int x : candidate.keySet()) count.put(x, 0);

        for (int x : keys) {
            if (count.containsKey(x)) count.put(x,count.get(x) + 1);
        }

        for (int x : count.keySet()) {
            if (count.get(x) > keys.length / k) result.add(x);
        }
    }

    public int[] candidates() {
        return this.result.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
//        int[] keys = new int[] { 1, 2, 3, 1, 4, 5, 1, 6, 7, 8, 9, 1, 2, 2, 2, 2, 3, 3, 3, 3 };
        int[] keys = new int[] { 1, 1, 3, 4, 5, 6, 7, 8, 9, 10 };
        DecimalDominants decimalDominants = new DecimalDominants(keys, 10);
        int[] candidates = decimalDominants.candidates();
        for (int i = 0; i < candidates.length; i++) {
            StdOut.println(candidates[i]);
        }
    }
}
