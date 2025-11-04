package PerfectBalanceRedBlackTree;

import MergeSort.CollinearPoint.Point;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Interval1D;

import java.util.ArrayList;
import java.util.Iterator;

public class IntervalST <Key extends Comparable<Key>, Value> {
    private class Node {
        Interval1D interval;
        Value val;
        double maxEndPoint;
        Node left, right;

        public Node(Interval1D k, Value v) {
            interval = k;
            val = v;
            maxEndPoint = k.max();
        }
    }

    private Node root;

    private double maxEndPoint(Node x) {
        if (x == null) return 0.0;
        return x.maxEndPoint;
    }

    private double toDouble(Key k) {
        if (k instanceof Number) return ((Number) k).doubleValue();
        throw new IllegalArgumentException("Key must be numeric");
    }


    public void put(Key lo, Key hi, Value val) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        if (lo.compareTo(hi) >= 0) throw new IllegalArgumentException();
        if (val == null) throw new IllegalArgumentException();

        root = put(root, new Interval1D(toDouble(lo), toDouble(hi)), val);
    }

    private Node put(Node x, Interval1D key, Value val) {
        if (x == null) {
            return new Node(key, val);
        }

        if (x.interval.equals(key)) {
            x.val = val;
            return x;
        }

        int cmp = Interval1D.MIN_ENDPOINT_ORDER.compare(key, x.interval);
        if (cmp <= 0) x.left = put(x.left, key, val);
        else x.right = put(x.right, key, val);

        double max = Math.max(maxEndPoint(x.left), maxEndPoint(x.right)); // max endpoint from left & right sub tree
        x.maxEndPoint = Math.max(max, x.maxEndPoint); // max end point either itself or somthing in it left or right subtree
        return x;
    }

    public Value get(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        Node x = get(root, new Interval1D(toDouble(lo), toDouble(hi)));
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, Interval1D key) {
        if (x == null) return null;
        if (x.interval.intersects(key)) return x;
        else if( x.left == null) return get(x.right, key);
        else if(maxEndPoint(x.left) < key.min()) return get(x.right, key);
        else return get(x.left, key);
    }

    public Iterable<Interval1D> intersects(Key lo, Key hi) {
        ArrayList<Interval1D> keys = new ArrayList<>();
        intersects(root, new Interval1D(toDouble(lo), toDouble(hi)), keys);
        return keys;
    }

    private void intersects(Node x, Interval1D key, ArrayList<Interval1D> result) {
        if (x == null) return;
        if (x.interval.intersects(key)) result.add(x.interval);
        intersects(x.left, key, result);
        intersects(x.right, key, result);

    }

    public static void main(String[] args) {
        IntervalST<Double, Double> st = new IntervalST<>();
        In in = new In("/home/decimal/personal/algorithms/temp/src/PerfectBalanceRedBlackTree/intervalst-input.txt");
        int n = in.readInt();

        for (int i = 0; i < n; i++) {
            double lo = in.readDouble();
            double hi = in.readDouble();
            st.put(lo, hi, lo+hi);
        }

        System.out.println(st.get(23.0, 25.0));
        System.out.println(st.get(12.0, 14.0));
        System.out.println(st.get(21.0, 23.0));

        for (Interval1D interval: st.intersects(5.0, 10.0)) {
            System.out.println(interval.toString());
        }

        // Add delete method and then implement sweeprectangle algoriths like sweepline algorithms
    }
}
