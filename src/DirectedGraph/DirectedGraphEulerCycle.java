package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class DirectedGraphEulerCycle {

    private Stack<Integer> cycle = null;
    private boolean isEulerianCycle = false;
    public DirectedGraphEulerCycle(Digraph G) {
        if (G.E() == 0) return;

        // To have a eulerian cycle necessary condition is each indegree(v) == outdegree(v)
        for (int v = 0; v < G.V(); v++) {
            if (G.indegree(v) != G.outdegree(v)) return;
        }

        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
        }

        int s = isolatedVertex(G);
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        cycle = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            cycle.push(v);
        }

        if (cycle.size() != G.V()) cycle = null;
    }

    private int isolatedVertex(Digraph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) > 0) return v;
        }
        return -1;
    }
}
