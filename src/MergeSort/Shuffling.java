package MergeSort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

class Node {
    Integer item;
    Node next;

    public Node(Integer value) {
        this.item = value;
    }
}

public class Shuffling<Item> {
    private static Node findMiddle(Node first) {
        if (first == null) return null;

        Node slow = first;
        Node fast = first;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static Node merge(Node firstHalf, Node secondHalf) {
        Node dummy = new Node(-1);
        Node runner = dummy;
        while (firstHalf != null && secondHalf != null) {
            if (StdRandom.uniformInt(0, 2) > 0) {
                runner.next = new Node(firstHalf.item);
                firstHalf = firstHalf.next;
            } else {
                runner.next = new Node(secondHalf.item);
                secondHalf = secondHalf.next;
            }
            runner = runner.next;
        }

        // Left Over
        if (firstHalf != null) {
            while (firstHalf != null) {
                runner.next = new Node(firstHalf.item);
                firstHalf = firstHalf.next;
                runner = runner.next;
            }
        }
        // Left Over
        if (secondHalf != null) {
            while (secondHalf != null) {
                runner.next = new Node(secondHalf.item);
                secondHalf = secondHalf.next;
                runner = runner.next;
            }
        }
        return dummy.next;
    }

    public static Node suffle(Node x) {
        if (x.next == null) return x;

        Node mid = findMiddle(x);
        Node secondHalf = mid.next;
        mid.next = null;

        Node first = suffle(x);
        Node second = suffle(secondHalf);
        return merge(first, second);
    }

    public static void main(String[] args) {
        Node first = new Node(1);
        first.next = new Node(2);
        first.next.next = new Node(3);
        first.next.next.next = new Node(4);

        Node x = Shuffling.suffle(first);

        while (x != null) {
            StdOut.println(x.item);
            x = x.next;
        }

    }
}
