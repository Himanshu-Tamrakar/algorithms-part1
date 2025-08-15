package StacksAndQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] a;
    private int n;

    public ResizingArrayStack() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    private void resize(int capacity) {
        Item[] temArr = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temArr[i] = a[i];
        }
        a = temArr;
    }

    public void push(Item item) {
        if (isFull()) resize(a.length * 2);
        a[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[n-1]; // prevent loitering
        a[n] = null;
        n--;
        if (n > 0 && n == a.length/4) resize(a.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isFull() {
        return n == a.length;
    }

    public int size() {
        return n;
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayTraverse();
    }

    private class ReverseArrayTraverse implements Iterator<Item> {
        int i = n - 1;
        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
