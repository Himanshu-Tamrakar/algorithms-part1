package Trie;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TST<Value> {
    private Node<Value> root;
    private int n;
    private static class Node<Value> {
        char ch;
        Value val;
        Node<Value> left, mid, right;
    }

    public int size() {
        return n;
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException();
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }
    private Node<Value> get(Node<Value> x, String key, int d) {
        if (x == null) return null;

        char ch = key.charAt(d);
        if (ch < x.ch) return get(x.left, key, d);
        else if (ch > x.ch) return get(x.right, key, d);
        else if (d < key.length()-1) return get(x.mid, key, d+1);
        else return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val, 0);
    }
    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        char ch = key.charAt(d);
        if (x == null) {
            if (key.length()-1 == d) n++;
            x = new Node<>();
            x.ch = ch;
        }
        if (ch < x.ch) x.left = put(x.left, key, val, d);
        else if (ch > x.ch) x.right = put(x.right, key, val, d);
        else if (d < key.length()-1) x.mid = put(x.mid, key, val, d+1);
        else x.val = val;
        return x;
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> results = new Queue<>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null) return results;
        if (x.val != null) results.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        collect(x.left, prefix, results);
        prefix.append(x.ch);
        if (x.val != null) results.enqueue(prefix.toString());
        collect(x.mid, prefix, results);
        prefix.deleteCharAt(prefix.length()-1);
        collect(x.right, prefix, results);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        if (pattern == null) throw new IllegalArgumentException();
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(), 0, pattern, results);
        return results;
    }

    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> results) {
        if (x == null) return;
        char ch = pattern.charAt(i);
        if (ch == '.' || ch < x.ch) collect(x.left, prefix, i, pattern, results);
        if (ch == '.' || ch ==x.ch) {
            if (i == pattern.length() - 1 && x.val != null) results.enqueue(prefix.toString() + x.ch);
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.ch), i+1, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (ch == '.' || ch > x.ch) collect(x.right, prefix, i, pattern, results);
    }



//    public Iterable<String> keysThatMatch(String pattern) {
//        if (pattern == null) throw new IllegalArgumentException();
//        Queue<String> results = new Queue<>();
//        collect(root.mid, new StringBuilder(root.ch+""), pattern, results);
//        return results;
//    }
//    private void collect(Node<Value> x, StringBuilder prefix, String pattern, Queue<String> results) {
//        if (x == null) return;
//        int d = prefix.length();
//        char ch = pattern.charAt(d);
//        if (ch == '.') {
//            collect(x.left, prefix, pattern, results);
//            prefix.append(x.ch);
//            if (pattern.length() == prefix.length() && x.val != null) results.enqueue(prefix.toString());
//            collect(x.mid, prefix, pattern, results);
//            prefix.deleteCharAt(prefix.length()-1);
//            collect(x.right, prefix, pattern, results);
//        } else {
//            prefix.append(x.ch);
//            if (pattern.length() == prefix.length() && x.val != null) results.enqueue(prefix.toString());
//            collect(x.mid, prefix, pattern, results);
//            prefix.deleteCharAt(prefix.length()-1);
//        }
//
//    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;
        root = delete(root, key, 0);
    }

    private Node<Value> delete(Node<Value> x, String key, int d) {
        if (x == null) return null;
        char ch = key.charAt(d);
        if (ch < x.ch) x.left = delete(x.left, key, d);
        else if (ch > x.ch) x.right = delete(x.right, key, d);
        else if (d < key.length()-1) x.mid = delete(x.mid, key, d+1);
        else {
            if (x.val != null) n--;
            x.val = null;
        }
        if (x.left == null && x.mid == null && x.right == null) return null;
        return x;
    }

    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException();
        int d = longestPrefixOf(root, query, 0, -1);
        if (d == -1) return null;
        return query.substring(0, d);
    }

    private int longestPrefixOf(Node<Value> x, String query, int d, int len) {
        if (x == null) return len;
        if (d == query.length()) return len;
        if (x.val != null) len = d;
        char ch = query.charAt(d);
        if (ch < x.ch) return longestPrefixOf(x.left, query, d, len);
        else if(ch > x.ch) return longestPrefixOf(x.right, query, d, len);
        else return longestPrefixOf(x.mid, query, d+1, len);

    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TST<Integer> st = new TST<Integer>();
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

        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
        StdOut.println();
//
        StdOut.println("keysWithPrefix(\"she\"):");
        for (String s : st.keysWithPrefix("she"))
            StdOut.println(s);
        StdOut.println();
//
        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}
