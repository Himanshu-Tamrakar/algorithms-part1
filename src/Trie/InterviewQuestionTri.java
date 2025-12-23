package Trie;


import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class InterviewQuestionTri {


    /**
     * Question 1
     * Prefix free codes
     * In data compression, a set of binary strings is if no string is a prefix of another. For example, {01,10,0010,1111} is prefix free,
     * but {01,10,0010,10100} is not because 10 is a prefix of 10100.
     * Design an efficient algorithm to determine if a set of binary strings is prefix-free.
     * The running time of your algorithm should be proportional the number of bits in all of the binary stings.
     */
    private static class PrefixFreeCode {
        Alphabet alphabet = Alphabet.BINARY;
        private class Node {
            Node[] next = new Node[alphabet.radix()];
            int val;
        }
        Node root;

        public void put(String key, int val) {
            root = put(root, key, val, new StringBuilder(), 0);
        }

        private Node put(Node x, String key, int val, StringBuilder prefix, int d) {
            if (x == null) x = new Node();

            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char ch = key.charAt(d);
            if (x.val != 0) System.out.println("is Not prefix tree");
            x.next[alphabet.toIndex(ch)] = put(x.next[alphabet.toIndex(ch)], key, val, prefix.append(ch), d+1);
            return x;
        }

        public int get(String key) {
            if (key == null) throw new IllegalArgumentException();

            Node x = get(root, key, 0);
            if (x == null) return -111;
            return x.val;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (key.length() == d) return x;

            char ch = key.charAt(d);
            int index = alphabet.toIndex(ch);
            return get(x.next[index], key, d+1);
        }
    }


    private static class Boggle {
         private TST<Boolean> st;
         private char[][] boggle;
         private static final int ROW = 3;
         private static final int COL = 3;

         public Boggle(String[] dictionary, char[][] boggle) {
             this.st = new TST<>();
             this.boggle = new char[3][3];
             for (String s: dictionary)
                 st.put(s, true);
             for (int i = 0; i < boggle.length; i++)
                 for (int j = 0; j < boggle[0].length; j++)
                     this.boggle[i][j] = boggle[i][j];
             StdOut.println();
         }

         public Iterable<String> boggleResult() {
             Queue<String> queue = new Queue<>();
             for (int i = 0; i < ROW; i++) {
                 for (int j = 0; j < COL; j++) {
                     boolean[][] visited = new boolean[ROW][COL];
                     boggleResult(queue, "", i, j, visited);
                 }
             }
             return queue;
         }

         private void boggleResult(Queue<String> queue, String match, int r, int c, boolean[][] visited ) {
             if(r == ROW || c == COL) return;
             visited[r][c]=true;
             match = match + this.boggle[r][c];
             if(this.st.get(match) != null && this.st.get(match)) queue.enqueue(match);;
             if(isValid(r-1, c) && !visited[r-1][c]) {
                 boggleResult(queue,match, r-1, c, visited);
             }
             if(isValid(r+1, c) && !visited[r+1][c]) {
                 boggleResult(queue,match, r+1, c, visited);
             }
             if(isValid(r, c-1) && !visited[r][c-1]) {
                 boggleResult(queue,match, r, c-1, visited);
             }
             if(isValid(r, c+1) && !visited[r][c+1]) {
                 boggleResult(queue,match, r, c+1, visited);
             }
             if(isValid(r-1, c-1) && !visited[r-1][c-1]) {
                 boggleResult(queue,match, r-1, c-1, visited);
             }
             if(isValid(r-1, c+1) && !visited[r-1][c+1]) {
                 boggleResult(queue,match, r-1, c+1, visited);
             }
             if(isValid(r+1, c-1) && !visited[r+1][c-1]) {
                 boggleResult(queue,match, r+1, c-1, visited);
             }
             if(isValid(r+1, c+1) && !visited[r+1][c+1]) {
                 boggleResult(queue,match, r+1, c+1, visited);
             }
             visited[r][c] = false;
         }

         private boolean isValid(int r, int c) {
             return r > -1 && r < ROW && c > -1 && c < COL ? true : false;
         }

         public static void main(String[] args) {
             String[] dictionary = {"PEEKS", "FOR", "QUIZ", "GO"};
             char[][] boggle = { {'P', 'I', 'Z'}, {'U', 'E', 'K'}, {'Q', 'S', 'E'} };
             Boggle b = new Boggle(dictionary, boggle); for (String s: b.boggleResult()) { StdOut.println(s);
             }
         }
    }

    public static void main(String[] args) {
        PrefixFreeCode prefixFreeCode = new PrefixFreeCode();
        String[] codes = new String[] {"01","10","0010","1111"};
        int i = 0;
        for (String s: codes) prefixFreeCode.put(s, i++);

        PrefixFreeCode prefixFreeCode1 = new PrefixFreeCode();
        codes = new String[] {"01","10","0010","10100"};
        for (String s: codes) prefixFreeCode1.put(s, i++);

    }


}
