package StacksAndQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] q;
    private int n;
    private int first;
    private int last;
    public ResizingArrayQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last  = n;
    }

    public boolean isFull() {
        return n == q.length;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void enqueue(Item item) {
        if (isFull()) resize(q.length * 2);
        q[last++] = item;
        if (last == q.length) last = 0; // wrap around
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first++] = null;
        n--;
        if (first == q.length) first = 0;           // wrap-around
        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[first];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }




}
