package PriorityQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class RandamizeMaxPQ<Key> {
    private Key[] pq;
    private int sz;

    public RandamizeMaxPQ(int initCapacity) {
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

    public int sample() {
        int index = StdRandom.uniformInt(1, sz);
        return index;
    }

    public Key delRandom(int k) {
        Key item = pq[k];
        pq[k] = pq[sz--];
        if (k > 1 && less(k/2, k)) swim(k);
        else sink(k);
        return item;
    }

    public static void main(String[] args) {
        RandamizeMaxPQ<Integer> pq = new RandamizeMaxPQ<>(20);
        pq.insert(9); pq.insert(6); pq.insert(3); pq.insert(19);
        StdOut.println("(" + pq.size() + " left on pq)");
        int random = pq.sample();
        StdOut.println("Random index: " + random);
        StdOut.println("Delete random: " + random + " value: " + pq.delRandom(random));
        StdOut.println("Remaining: " + pq.size() + " , Item: " + pq.delMax());
        StdOut.println("Remaining: " + pq.size() + " , Item: " + pq.delMax());
    }
}
