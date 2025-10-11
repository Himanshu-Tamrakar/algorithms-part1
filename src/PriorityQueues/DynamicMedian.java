package PriorityQueues;


import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class DynamicMedian {
    private MinPQ<Integer> minPQ;
    private MaxPQ<Integer> maxPQ;

    public DynamicMedian() {
        minPQ = new MinPQ(10);
        maxPQ = new MaxPQ(10);
    }

    public double getMedian() {
        if ((minPQ.size() + maxPQ.size()) % 2 == 0) {
            return (double) (minPQ.min() + maxPQ.max()) / 2;
        } else {
            return (double) maxPQ.max();
        }
    }


    public void insert(int x) {
        if (maxPQ.isEmpty() || x <= maxPQ.max()) {
            maxPQ.insert(x);
        } else {
            minPQ.insert(x);
        }

        // rebalancing
        // plus making sure left half i.e maxPQ has always grater elements than min PQ
        if (maxPQ.size() > minPQ.size() + 1) {
            minPQ.insert(maxPQ.delMax());
        } else if (minPQ.size() > maxPQ.size()) {
            maxPQ.insert(minPQ.delMin());
        }
    }


    public static void main(String[] args) {

        DynamicMedian dynamicMedian = new DynamicMedian();

        int[] input = {3, 4, 7};
        for (int x : input) {
            dynamicMedian.insert(x);
            StdOut.println("Total Elements: " + (dynamicMedian.minPQ.size() + dynamicMedian.maxPQ.size()));
            StdOut.println("Currently median: " + dynamicMedian.getMedian());
        }
    }



}
