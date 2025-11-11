package UdirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class UndirectedEulerianCycle {
    private Stack<Integer> cycle = new Stack<>();
    private static class Edge {
        private final int v;
        private final int w;
        private boolean isUsed;
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            this.isUsed = false;
        }

        public int other(int vertex) {
            if (vertex == this.v) return this.w;
            else if (vertex == this.w) return this.v;
            else throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    public UndirectedEulerianCycle(Graph G) {
        if (G.E() == 0) return;

        // necessary condition: all vertices have even degree
        // (this test is needed, or it might find an Eulerian path instead of cycle)
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) return;
        }

        // create local view of adjacency lists, to iterate one vertex at a time
        // the helper Edge data type is used to avoid exploring both copies of an edge v-w
        Queue<Edge>[] adj = (Queue<Edge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new Queue<Edge>();
        }
        for (int v = 0; v < G.V(); v++) {
            int selfLoop = 0;
            for (int w: G.adj(v)) {
                if (v == w) {
                    if (selfLoop % 2 == 0) {
                        Edge e = new Edge(v, w);
                        adj[v].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    selfLoop++;
                } else if (v < w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }

        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        cycle = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (!adj[v].isEmpty()) {
                Edge e = adj[v].dequeue();
                if (e.isUsed) continue;
                e.isUsed = true;
                stack.push(v);
                v = e.other(v);
            }
            cycle.push(v);
        }

        if (cycle.size() != G.E() + 1)
            cycle = null;


    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    private int nonIsolatedVertex(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) return v;
        }
        return -1;
    }



}
