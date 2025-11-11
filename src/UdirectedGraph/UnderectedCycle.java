package UdirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class UnderectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    public UnderectedCycle(Graph G) {
        if (hasParallelEdge(G)) return;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfs(G, -1, v);
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    private void dfs(Graph G, int u, int v) {
        marked[v] = true;
        for (int w: G.adj(v)) {
            if (cycle != null) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, v, w);
            } else if (u != w) { // u is its parent
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
    }

    private boolean hasParallelEdge(Graph G) {
        boolean[] marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            for (int w: G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }

            for (int w : G.adj(v)) {
                marked[w] = false;
            }
        }
        return false;
    }
}
