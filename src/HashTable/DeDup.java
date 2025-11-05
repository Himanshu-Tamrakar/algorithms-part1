package HashTable;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class DeDup {
        // Do not instantiate.
        private DeDup() { }

        public static void main(String[] args) {
            TreeSet<String> set = new TreeSet<>();

            // read in strings and add to set
            while (!StdIn.isEmpty()) {
                String key = StdIn.readString();
                if (!set.contains(key)) {
                    set.add(key);
                    StdOut.println(key);
                }
            }
        }

}
