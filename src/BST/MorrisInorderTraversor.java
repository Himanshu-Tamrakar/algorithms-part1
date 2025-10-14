/**
 * Question 3
 * Inorder traversal with constant extra space. Design an algorithm to perform an inorder traversal of a binary search tree using only a constant
 * amount of extra space.
 * For Reference: https://takeuforward.org/data-structure/morris-inorder-traversal-of-a-binary-tree/
 */
package BST;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MorrisInorderTraversor {
    private static class Node {
        int key;
        Node left, right;
        public Node(int k) {key = k;}
    }

    private Node root;

    public MorrisInorderTraversor() {
        root = null;
    }

    public Iterable<Integer> morrisInorder() {

        return morrisInorder(root);
    }

    private Iterable<Integer> morrisInorder(Node x) {
        ArrayList<Integer> inorder = new ArrayList<>();
        while (x != null) {
            if (x.left == null) {
                inorder.add(x.key);
                x = x.right;
            } else {
                Node predecessor = x.left;
                while (predecessor.right != null && predecessor.right != x) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    predecessor.right = x; // Temporary creating to go to parent
                    x = x.left;
                } else {
                    predecessor.right = null; // Removing the link
                    inorder.add(x.key);
                    x = x.right;
                }
            }
        }

        return inorder;
    }

    public static void main(String[] args) {
        MorrisInorderTraversor morrisInorderTraversor = new MorrisInorderTraversor();
        morrisInorderTraversor.root = new Node(10);
        morrisInorderTraversor.root.left = new Node(2);
        morrisInorderTraversor.root.right = new Node(13);

        morrisInorderTraversor.root.left.left = new Node(1);
        morrisInorderTraversor.root.left.right = new Node(4);
        morrisInorderTraversor.root.left.right.left = new Node(3);
        morrisInorderTraversor.root.left.right.right = new Node(8);

        for (int k : morrisInorderTraversor.morrisInorder()) {
            StdOut.println(k);
        }

    }
}
