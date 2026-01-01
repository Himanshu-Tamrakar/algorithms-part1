

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.RedBlackBST;

public class MoveToFront {

    private static final int R = 256;

    public static void encode() {
        RedBlackBST<Integer, Character> rbt = new RedBlackBST<>();
        int[] map = new int[R];
        for (char i = 0; i < R; i++) {
            rbt.put((int) i, i);
            map[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int key = map[c];
            BinaryStdOut.write(rbt.rank(key), 8);
            rbt.delete(key);
            map[c] = rbt.min() - 1;
            rbt.put(map[c], c);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        RedBlackBST<Integer, Character> rbt = new RedBlackBST<>();
        for (char i = 0; i < R; i++) {
            rbt.put((int) i, i);
        }
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            Integer key = rbt.select(i);
            char c = rbt.get(key);
            BinaryStdOut.write(c);
            rbt.delete(key);
            rbt.put(rbt.min() - 1, c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}