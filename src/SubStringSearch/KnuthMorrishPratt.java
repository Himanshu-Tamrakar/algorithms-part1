package SubStringSearch;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KMP;

public class KnuthMorrishPratt {
    private final int R = 256;
    int[][] dfa;
    private int M;
    public KnuthMorrishPratt(String pattern) {
        M = pattern.length();
        dfa = new int[R][pattern.length()];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < pattern.length(); j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X]; // Mismatch char
            dfa[pattern.charAt(j)][j] = j+1; // Match Char
            X = dfa[pattern.charAt(j)][X]; // update X
        }
    }

    public int search(String text) {
        int i, j;
        for (i = 0, j = 0; i < text.length() && j < M; i++) {
            char ch = text.charAt(i);
            j = dfa[ch][j];
        }
        if (j == M) return i - M;
        return text.length();
    }

    public int search(In in) {
        int i, j;
        for (i = 0, j = 0; !in.isEmpty() && j < M; i++) {
            char ch = in.readChar();
            j = dfa[ch][j];
        }

        if (j == M) return i - M;
        return i;
    }

    public static void main(String[] args) {
        String text = "MY NAME IS HIMANSHU TAMRAKAR. I LIVE IN RAMPUR BAGHELAN DISTRICT SATNA MP. MY PIN CODE IS 485115. ATTACH AT DOWN. I WORK AS SOFTWARE ARCHITECT";
        String pattern = "ATTACH AT DOWN";

        KnuthMorrishPratt knuthMorrishPratt = new KnuthMorrishPratt(pattern);
        int i = knuthMorrishPratt.search(text);
        if (i < text.length()) {
            System.out.println(text.substring(i, i+pattern.length()));
        } else {
            System.out.println("Not Found!");
        }
    }

}
