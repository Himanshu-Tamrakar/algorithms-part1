package SubStringSearch;

import edu.princeton.cs.algs4.StdOut;

public class BoyerMoore {
    private final int R = 256;
    private int[] right;
    private String pat;
    private int M;

    public BoyerMoore(String pat) {
        this.pat = pat;
        M = pat.length();
        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int i = 0; i < pat.length(); i++) {
            right[pat.charAt(i)] = i;
        }
    }

    public int search(String text) {
        int skip = 0;
        int m = M;
        int n = text.length();
        for (int i = 0; i <= n-m; i += skip) {
            skip = 0;

            for (int j = m-1; j >= 0 ; j--) {
                if (pat.charAt(j) != text.charAt(i+j)) {
                    skip = Math.max(1, j - right[text.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }

        return n;
    }

    public static void main(String[] args) {
        String pattern = "NEEDLE";
        String text = "NOPNEEHJFNEEDLEOPOPO";
        BoyerMoore boyermoore1 = new BoyerMoore(pattern);
        int offset1 = boyermoore1.search(text);
        System.out.println(text.substring(offset1, offset1 + pattern.length()));
    }
}
