package BST;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

public class IsBST {
    static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    Node root; // root of the tree


    IsBST() {
        root = null;
    }


    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, -1, -1);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: elegant solution due to Bob Dondero
    private boolean isBST(Node x, int min, int max) {
        if (x == null) return true;
        if (min != -1 && x.key <= min) return false;
        if (max != -1 && x.key >= max) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // --- Main for manual testing ---
    public static void main(String[] args) {
        IsBST tree = new IsBST();
        tree.root = new Node(10);
        tree.root.left = new Node(5);
        tree.root.right = new Node(15);
        tree.root.right.left = new Node(3);
        tree.root.right.left.right = new Node(20);

        System.out.println(tree.isBST());

    }
}

