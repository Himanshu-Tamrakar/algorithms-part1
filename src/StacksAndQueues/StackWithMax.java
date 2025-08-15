package StacksAndQueues;

import java.util.NoSuchElementException;

public class StackWithMax {
    private Node first;
    private int n;
    private Double max = -1.0;

    private class Node {
        Double item;
        Node next;
    }

    public StackWithMax() {
        first = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void push(Double item) {
        if (max < item) max = item;
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        first = newNode;
        n++;
    }

    public Double pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Double item = first.item;
        first = first.next;
        n--;
        if (item.equals(max)) {
            // Recompute max
            max = -1.0;
            Node temp = first;
            while (temp != null) {
                if (temp.item > max) max = temp.item;
                temp = temp.next;
            }
        }
        return item;
    }

    public Double max() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return max;
    }
}
