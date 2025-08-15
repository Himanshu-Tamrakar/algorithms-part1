package StacksAndQueues;

import UF.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FixedCapacityStackOfString implements Iterable<String> {
    private String[] a;
    private int n;
    public FixedCapacityStackOfString(int n) {
        a = new String[n];
        n = 0;
    }

    public void push(String item) {
        a[n++] = item;
    }

    public String pop() {
        return a[--n];
    }

    public boolean isEmpty() {
        return  n == 0;
    }

    public boolean isFull() {
        return n == a.length;
    }

    public String peek() {
        return a[n-1];
    }

    public Iterator<String> iterator() {
        return new ReversArrayIterator();
    }

    private class ReversArrayIterator implements Iterator<String> {
        private int i = n-1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        FixedCapacityStackOfString stack = new FixedCapacityStackOfString(max);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (stack.isEmpty())  StdOut.println("BAD INPUT");
            else                       StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what's left on the stack
        StdOut.print("Left on stack: ");
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
