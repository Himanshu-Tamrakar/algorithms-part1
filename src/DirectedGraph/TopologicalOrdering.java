package DirectedGraph;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class TopologicalOrdering {
    private Iterable<Integer> order;  // topological order
    private int[] rank;
    public TopologicalOrdering(Digraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v : order)
                rank[v] = i++;
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public boolean isDAG() {
        return hasOrder();
    }

    public int rank(int v) {
        if (hasOrder()) return rank[v];
        else return -1;
    }
}
