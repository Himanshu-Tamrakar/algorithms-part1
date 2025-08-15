package StacksAndQueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Parentheses {
    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';
    private static final char LEFT_BRACE     = '{';
    private static final char RIGHT_BRACE    = '}';
    private static final char LEFT_BRACKET   = '[';
    private static final char RIGHT_BRACKET  = ']';

    public static void main(String[] args) {
        String input = StdIn.readLine();
        StdOut.println(isBalanced(input));
    }

    public static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if      (s.charAt(i) == LEFT_BRACKET) stack.push(s.charAt(i));
            else if (s.charAt(i) == LEFT_BRACE) stack.push(s.charAt(i));
            else if (s.charAt(i) == LEFT_PAREN) stack.push(s.charAt(i));
            else if (s.charAt(i) == RIGHT_BRACKET) {
                if  (stack.isEmpty()) return false;
                if  (stack.pop() != LEFT_BRACKET) return false;
            }
            else if (s.charAt(i) == RIGHT_BRACE) {
                if (stack.isEmpty()) return false;
                if(stack.pop() != LEFT_BRACE) return false;
            }
            else if (s.charAt(i) == RIGHT_PAREN) {
                if (stack.isEmpty()) return false;
                if(stack.pop() != LEFT_PAREN) return false;
            }
        }
        return true;
    }

}
