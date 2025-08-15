package StacksAndQueues;

import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueWithTwoStack<Item> implements Iterable<Item> {
    private Stack<Item> stack1;
    private Stack<Item> stack2;
    private int n;

    public QueueWithTwoStack() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void enqueue(Item item) {
        stack1.push(item);
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        n--;
        return stack2.pop();
    }

    @Override
    public Iterator<Item> iterator() {
        return new TwoStackTraverse();
    }

    private class TwoStackTraverse implements Iterator<Item> {
        private Stack<Item> copyStack;

        public TwoStackTraverse() {
            Stack<Item> temp = new Stack<>();
            copyStack = new Stack<>();
            // Transfer stack2 contents (already in FIFO order)
            for (Item item : stack2) {
                copyStack.push(item);
            }

            // Transfer stack1 contents (LIFO), so we reverse it to get correct FIFO
            for (Item item : stack1) {
                temp.push(item);
            }
            while (!temp.isEmpty()) {
                copyStack.push(temp.pop());
            }
        }

        @Override
        public boolean hasNext() {
            return !copyStack.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copyStack.pop();
        }
    }

    public static void main(String[] args) {
        QueueWithTwoStack<Integer> q = new QueueWithTwoStack<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.dequeue();
        q.enqueue(4);

        for (int item : q) {
            System.out.println(item); // Should print 2, 3, 4
        }
    }
}
