package PriorityQueues;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class MaxPQ<Key>  {
    private Key[] pq;
    private int sz;

    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        sz = 0;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    public void insert(Key x) {

        // double size of array if necessary
        if (sz == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++sz] = x;
        swim(sz);

    }

    /**
     * Removes and returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        exch(1, sz--);
        sink(1);
        pq[sz+1] = null;     // to avoid loitering and help with garbage collection
        if ((sz > 0) && (sz == (pq.length - 1) / 4)) resize(pq.length / 2);

        return max;
    }

    private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= sz; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= sz) {
            int j = 2 * k;
            if (j < sz && less(j, j+1)) j++;
            if (!less(k, j)) return;
            exch(k, j);
            k = j;
        }

    }

    private boolean less(int i, int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<>(20);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
