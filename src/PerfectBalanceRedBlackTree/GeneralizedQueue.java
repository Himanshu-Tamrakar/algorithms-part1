package PerfectBalanceRedBlackTree;

import edu.princeton.cs.algs4.RedBlackBST;

public class GeneralizedQueue<Key extends Comparable<Key>, Value> {
    RedBlackBST<Key, Value> st;

    public GeneralizedQueue() {
        st = new RedBlackBST<>();
    }

    public void addToFront(Key key, Value val) {
        st.put(key, val);
    }
    public void addToBack(Key key, Value val) {
        st.put(key, val);
    }
    public void removeItemFromFront() {
        st.deleteMin();
    }
    public Key get(int rank) {
        return st.select(rank);
    }

    public void delete(int rank) {
        st.delete(st.select(rank));
    }

}
