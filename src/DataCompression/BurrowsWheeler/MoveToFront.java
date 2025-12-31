package DataCompression.BurrowsWheeler;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.RedBlackBST;

public class MoveToFront {
    private static final int R = 256;
    public static void encode() {
        RedBlackBST<Integer, Character> st = new RedBlackBST<>();
        int[] map = new int[R]; // It is mapping of character to integer value
        for (int i = 0; i < R; i++) {
            st.put(i, (char)i);
            map[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readChar();
            int key = map[c]; // current integer representation of character
            BinaryStdOut.write((char )st.rank(key));
            st.delete(key);
            map[c] = st.min()-1;
            st.put(map[c], (char) c);
        }

        BinaryStdOut.close();
    }

    public static void decode() {
        RedBlackBST<Integer, Character> st = new RedBlackBST<>();
        for (int i = 0; i < R; i++) {
            st.put(i, (char)i);
        }

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readInt();
            int key = st.select(i);
            char ch = st.get(key);
            BinaryStdOut.write(ch);
            int k = st.min()-1;
            st.delete(key);
            st.put(k, ch);
        }
        BinaryStdOut.close();

    }

    public static void main(String[] args) {
        if (args[0].equals( "-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("");
    }
}
