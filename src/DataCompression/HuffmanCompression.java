package DataCompression;


import edu.princeton.cs.algs4.*;

/**
 * Fix length key and veriable length coding
 *
 * Problem 1: How to construct a prefix-free code using variable-length encoding with a trie.
 * expand()
 *
 * Problem 2: How to transmit this trie over a network, either as a symbol table (ST) or as a binary-encoded stream. We use Shannon–Fano binary encoding for this purpose.
 * writeTrie()
 *
 * Problem 3: How to reconstruct the trie from the transmitted binary-encoded stream.
 * readTrie()
 *
 * Problem 4: How to encrypt (encode) text using the prefix-free code generated from the trie.
 *
 * Problem 5: How to decode (expand) the encoded data back into the original text.
 *
 */
public class HuffmanCompression {
    private static final int R = 256;
    private static Node root;
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }

        root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st, root, "");

        writeTrie(root);

        // print number of bytes in original uncompressed message
        BinaryStdOut.write(input.length);

        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                }
                else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                }
                else throw new IllegalStateException("Illegal state");
            }
        }

        // close output stream
        BinaryStdOut.close();

    }

    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
        }
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < freq.length; c++) {
            if (freq[c] > 0) pq.insert(new Node(c, freq[c], null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    public static void expand() {
        Node root = readtrie();
        int N = BinaryStdIn.readInt();

        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            BinaryStdOut.close();
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);

    }

    private static Node readtrie() {
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        } else {
            return new Node('\0', 0, readtrie(), readtrie());
        }
    }


    public static void main(String[] args) {
        System.out.println("Enter ABRACADABRA!");
        HuffmanCompression.compress();
//        String s = BinaryStdIn.readString();
//        if (s.equals("-")) compress();
//        else if ("")
//        if      (args[0].equals("-")) compress();
//        else if (args[0].equals("+")) expand();
//        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
