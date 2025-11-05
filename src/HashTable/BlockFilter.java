package HashTable;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class BlockFilter {


        // Do not instantiate.
        private BlockFilter() { }

        public static void main(String[] args) {
            TreeSet<String> set = new TreeSet<>();

            // read in strings and add to set
            In in = new In(args[0]);
            while (!in.isEmpty()) {
                String word = in.readString();
                set.add(word);
            }

            // read in string from standard input, printing out all exceptions
            while (!StdIn.isEmpty()) {
                String word = StdIn.readString();
                if (!set.contains(word))
                    StdOut.println(word);
            }
        }

}
