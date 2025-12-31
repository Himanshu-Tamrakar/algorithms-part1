package DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.PriorityQueue;

public class InterviewQuestionDataCompression {


    /**
     * Combine smalles three posibilities instead od 2, which mean do delMin 3 times for left, mid, and right.
     * Create intermediate node using these three nodes. while moving left
     * write 0 with 2 bit(BinaryStdOut.write(0, 2)),
     * write 1 with 2 bit(BinaryStdOut.write(1, 2)),
     * write 2 with 2 bit(BinaryStdOut.write(2, 2))
     *
     * while expand read int each 2 bits to check it is 0 or 1 or 2
     *
     */
    public static class TernaryHuffmanCode {
        private static final int R = 256;
        private static Node root;
        public static class Node implements Comparable<Node> {
           private final char ch;
           private final int freq;
           private final Node left, mid, right;

           public Node(char ch, int freq, Node left, Node mid, Node right) {
               this.ch = ch;
               this.freq = freq;
               this.left = left;
               this.mid = mid;
               this.right = right;
           }

           public boolean isLeaf() {
               return left == null && mid == null && right == null;
           }

           @Override
           public int compareTo(Node that) {
               return this.freq - that.freq;
           }
        }

        public static void compress() {
            // step: 1
            String s = BinaryStdIn.readString();
            char[] input = s.toCharArray();

            // step: 2
            int[] freq = new int[R];
            for (int i = 0; i < input.length; i++) {
                freq[input[i]]++;
            }

            //step: 3
            root = buildTrie(freq);
            String [] st = new String[R];
            buildCode(st, root, "");

            // step 4
            writeTrie(root);

            // step: 5
            BinaryStdOut.write(input.length);

            // step: 6: use Huffman code to encode input
            for (int i = 0; i < input.length; i++) {
                String code = st[input[i]];
                for (int j = 0; j < code.length(); j++) {
                    if (code.charAt(j) == '0') {
                        BinaryStdOut.write(0, 2);
                    }
                    else if (code.charAt(j) == '1') {
                        BinaryStdOut.write(1, 2);
                    }
                    else if (code.charAt(j) == '2') {
                        BinaryStdOut.write(2, 2);
                    }
                    else throw new IllegalStateException("Illegal state");
                }
            }

        }


        private static void writeTrie(Node x) {
            if (!x.isLeaf()) {
                BinaryStdOut.write(true);
                BinaryStdOut.write(x.ch, 8);
                return;
            }
            BinaryStdOut.write(false);
            writeTrie(x.left);
            writeTrie(x.mid);
            writeTrie(x.right);
        }

        private static void buildCode(String[] st, Node x, String s) {
            if (!x.isLeaf()) {
                buildCode(st, x.left, s + "0");
                buildCode(st, x.mid, s + "1");
                buildCode(st, x.right, s + "2");
            }

            st[x.ch] = s;
        }

        private static Node buildTrie(int[] freq) {
            MinPQ<Node> pq = new MinPQ<>();
            for (char c = 0; c < R; c++) {
                if (freq[c] > 0)
                    pq.insert(new Node(c, freq[c], null, null, null));
            }

            while (pq.size() > 1) {
                Node left = pq.delMin();
                Node mid = pq.delMin();
                Node right = null;
                if (pq.size() > 0)
                    right = pq.delMin();

                pq.insert(new Node('\0', left.freq + mid.freq + right.freq, left, mid, right));
            }

            return pq.delMin();

        }

        private static Node readtrie() {
            if (BinaryStdIn.readBoolean()) {
                char ch = BinaryStdIn.readChar();
                new Node(ch, 0, null, null, null);
            }
            Node left = readtrie();
            Node mid = readtrie();
            Node right = readtrie();
            return new Node('\0', 0, left, mid, right);
        }

        public static void expand() {
            // step: 1
            Node root = readtrie();

            // step: 2
            int N = BinaryStdIn.readInt();

            // step: 3
            for (int i = 0; i < N; i++) {
                Node x = root;
                while (!x.isLeaf()) {
                    int bit = BinaryStdIn.readInt(2);
                    if (bit == 0) x = x.left;
                    else if (bit == 1) x = x.mid;
                    else x = x.right;
                }
                BinaryStdOut.write(x.ch, 8);
            }
            BinaryStdOut.close();
        }
    }


    /**
     this problem could be solved by redblack tree, rank and select function.
     * rank function is to give a key, return the rank of the key.
     * select function is to give a rank, return the key which is on this rank.
     *
     * if we want to move to front, we can remove it from red black tree, and mark a (min - 1) key, to add this node back.
     * therefore, we need maintain a map for key(is 'a'-'z'), value is (key in red black tree)
     */
    private static class MoveToFrontEncoding {
        private static final int R = 256;

        public static void encode() {
            RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
            int[] map = new int[R];
            for (char c = 0; c < R; c++) {
                bst.put((int) c, c);
                map[c] = c;
            }

            while (!BinaryStdIn.isEmpty()) {
                char c = BinaryStdIn.readChar();
                int key = map[c];
                BinaryStdOut.write((char) bst.rank(key));
                bst.delete(key);
                map[c] = bst.min()-1;
                bst.put(map[c], c);
            }

            BinaryStdOut.close();
        }

        public static void decode() {
            RedBlackBST<Integer, Character> bst = new RedBlackBST<>();
            for (char i = 0; i < R; i++) {
                bst.put((int) i, i);
            }
            while (!BinaryStdIn.isEmpty()) {
                int i = BinaryStdIn.readChar();
                Integer key = bst.select(i);
                char c = bst.get(key);
                BinaryStdOut.write(c);
                bst.delete(key);
                bst.put(bst.min() - 1, c);
            }
            BinaryStdOut.close();

        }
    }



}
