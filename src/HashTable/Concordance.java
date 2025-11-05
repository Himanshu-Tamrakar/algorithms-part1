package HashTable;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Concordance {
    public static void main(String[] args) {
        int CONTEXT = 5;
        In in = new In(args[0]);
        String[] words = in.readAllStrings();
        HashMap<String, Queue<Integer>> st = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if (!st.containsKey(s)) {
                st.put(s, new Queue<Integer>());
            }
            Queue<Integer> queue = st.get(s);
            queue.enqueue(i);
        }
        StdOut.println("Finished building concordance");

        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            Queue<Integer> queue = st.get(query);
            if (queue == null) queue = new Queue<Integer>();
            for (int k : queue) {
                for (int i = Math.max(0, k - CONTEXT + 1); i < k; i++)
                    StdOut.print(words[i] + " ");
                StdOut.print("*" + words[k] + "* ");
                for (int i = k + 1; i < Math.min(k + CONTEXT, words.length); i++)
                    StdOut.print(words[i] + " ");
                StdOut.println();
            }
            StdOut.println();
        }
    }
}
