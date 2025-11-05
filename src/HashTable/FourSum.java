/**
 * Interview Questions: Hash Tables (ungraded)
 * Question 1
 * (under suitable technical assumptions):=> If there is no duplicates inputs present
 *  [10, 8, 15, 3, 16, 2]
 *
 */
package HashTable;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.HashMap;

public class FourSum {
    public static void main(String[] args) {
        HashMap<Integer, String> table = new HashMap<>();
        int[] inputs = new int[] {10, 8, 15, 3, 16, 2};
        int count = 0;
        for (int i = 0; i < inputs.length; i++) {
            for (int j = i+1; j < inputs.length; j++) {
                int key = inputs[i] + inputs[j];
                String val = inputs[i]+":"+inputs[j];
                if (table.containsKey(key)) {
                    String[] v = table.get(key).split(":");
                    int p = Integer.parseInt(v[0]);
                    int q = Integer.parseInt(v[1]);
                    if (inputs[i] != p && inputs[i] != q && inputs[j] != p && inputs[j] != q) {
                        count++;
                        System.out.println(inputs[i] + " + " + inputs[j] + "==" + p + " + " + q);
                    }
                } else {
                    table.put(key, val);
                }

            }
        }
        System.out.println("TOTAL: " + count );
    }
}
