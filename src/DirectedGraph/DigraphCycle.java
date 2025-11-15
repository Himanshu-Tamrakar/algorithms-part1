package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class DigraphCycle {
    private boolean[] marked;
    private boolean[] onStack;
    private Stack<Integer> cycle;

    public DigraphCycle(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w: G.adj(v)) {
            if (cycle != null) return;
            else if (!marked[w]) {
                dfs(G, w);
            } else if (onStack[w]) {
//                load cycle
                cycle = new Stack<>();
            }
        }

        onStack[v] = false;
    }


}
