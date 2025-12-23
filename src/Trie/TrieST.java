package Trie;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Good when we know each string length is less then less height. Like if we want to make distionary of 3 letter word like that.
public class TrieST<Value> {
    private static final int R = 256;
    private Node root;
    private int n;
    private static class Node {
        Node[] next = new Node[R];
        Object val;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException();

        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == d) return x;
        int c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) throw new IllegalArgumentException();
        root = put(root, key, val, 0);
    }
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (key.length() == d) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        int ch = key.charAt(d);
        x.next[ch] = put(x.next[ch], key, val, d+1);
        return x;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException();
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == d) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            int ch = key.charAt(d);
            x.next[ch] = delete(x.next[ch], key, d+1);
        }
        if (x.val != null) return x;
        for (int r = 0; r < R; r++) {
            if (x.next[r] != null) return x;
        }

        return null;

    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }


    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char r = 0; r < R; r++) {
            prefix.append(r);
            collect(x.next[r], prefix, results);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }


    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null) results.enqueue(prefix.toString());
        if (d == pattern.length()) return;
        char ch = pattern.charAt(d);

        if (ch == '.') {
            for (char r = 0; r < R; r++) {
                prefix.append(r);
                collect(x.next[r], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length()-1);
            }
        } else {
            prefix.append(ch);
            collect(x.next[ch], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    public String longestPrefixOf(String query) {
        int len = longestPrefixOf(root, query, 0, -1);
        if (len == -1) return null;
        else return query.substring(0, len);
    }

    private int longestPrefixOf(Node x, String query, int d, int len) {
        if (x == null) return len;
        if (x.val != null) len = d;
        if (d == query.length()) return len;
        char ch = query.charAt(d);
        return longestPrefixOf(x.next[ch], query, d+1, len);
    }



    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }


}
