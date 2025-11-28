// Consider Keys are keys stored in index pos where user inseted.
// pq is priority queue which tell what is min key based on keys index.
// where key which was inserted with index i presented in pq.
package PriorityQueues;

public class MinIndexPQ <Key extends Comparable<Key>> {
    private Key[] keys;
    private int[] pq;
    private int[] qp;
    private int n;

    public MinIndexPQ(int M) {
        keys = (Key[]) new Object[M+1];
        pq = new int[M+1];
        qp = new int[M+1];
        n = 0;
    }

    public void insert(int i, Key key) {

        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public void changeKey(int i, Key key) {
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Helper method
     */

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
