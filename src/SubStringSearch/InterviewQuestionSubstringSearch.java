package SubStringSearch;

import edu.princeton.cs.algs4.Queue;

public class InterviewQuestionSubstringSearch {

    /**
     * 1. If we check all permutation then ther will be n2 substring permutation and to check palindrop we need n to check palindrome.
     * Time Complexity: O(n^3)
     * Space Complexity: Constant
     *
     *
     * Feedback
     * Hint: Use Knuth-Morris-Pratt.
     */
    private class LongestPalindromSubsctring {

        // O(n^2) Solution
        // Checking center to outward charecter hence there will be n substring instead of n2 substring.
        public static int longestPaliddrom1(String s) {
            int res = 0;
            for (int i = 0; i < s.length(); i++) {

                int l = i-1;
                int r = i+1;
                while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                    res = Math.max(res, r-l+1);
                    l--;
                    r++;
                }

                l = i;
                r = i+1;
                while (l >=0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                    res = Math.max(res, r-l+1);
                    l--;
                    r++;
                }

            }

            return res;
        }

        private static boolean isOdd(int i) {
            return (i + 1) % 2 != 0;
        }

        public static void main(String[] args) {
            int res = LongestPalindromSubsctring.longestPaliddrom1("babad");
            System.out.println("Longest Palindrome of " + "`babad`" + "is :" + res);

            res = LongestPalindromSubsctring.longestPaliddrom1("baab");
            System.out.println("Longest Palindrome of " + "`baab`" + "is :" + res);
        }

    }

    /**
     *
     *
     * Feedback
     * Hint: use Knuth-Morris-Pratt.
     */
    private static class TandemRepeat {
        private int tandem ;
        public TandemRepeat(String s, String b) {
            int n = s.length();
            int m = b.length();
            // All position whose start is from from b[0]
            Queue<Integer> positions = new Queue<>();
            for (int i = 0; i <= s.length()-m; i++) {
                if (s.charAt(i) == b.charAt(0)) positions.enqueue(i);
            }

            Queue<Integer> subposition = new Queue<>();
            while (!positions.isEmpty()) {
                int i = positions.dequeue();
                int j = m-1;
                for (; j >= 0; j--) {
                    if (s.charAt(i+j) != b.charAt(j)) break;
                }
                if (j < 0) subposition.enqueue(i);
            }

            int consucative = 1;

            int pre = subposition.dequeue();
            while (!subposition.isEmpty()) {
                int curr = subposition.dequeue();

                if (pre+m == curr) {
                    tandem = Math.max(tandem, ++consucative);
                }
                else {
                    consucative = 1;
                }

                pre = curr;
            }
        }

        public boolean isTandem() {
            return tandem > 1;
        }

        public int tandemCount() {
            return tandem;
        }


        public static void main(String[] args) {
            TandemRepeat tandemRepeat = new TandemRepeat("abcabcababcaba", "abcab");
            System.out.println("String `abcabcababcaba` is tandem: " + tandemRepeat.isTandem() + " of " + "`abcab` string with count: " + tandemRepeat.tandemCount());

            tandemRepeat = new TandemRepeat("ababab", "ab");
            System.out.println("String `ababab` is tandem: " + tandemRepeat.isTandem() + " of " + "`ab` string with count: " + tandemRepeat.tandemCount());

            tandemRepeat = new TandemRepeat("aaaa", "a");
            System.out.println("String `aaaa` is tandem: " + tandemRepeat.isTandem() + " of " + "`a` string with count: " + tandemRepeat.tandemCount());
        }
    }


    /**
     *
     *
     * Feedback
     * Hint: use given a parameter L find all palindromic substrings of length exactly L in linear time using a Karp-Rabin strategy.
     * Hint (signing bonus): To do it in linear time in the worst case, use Manacher's algorithm or suffix trees.
     */
    private static class LongestPalindromicSubstring {

    }


    public static void main(String[] args) {
        LongestPalindromSubsctring.main(new String[]{});
        TandemRepeat.main(new String[]{});
    }

}

