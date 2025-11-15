package DirectedGraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class DirectedGraph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;
    public DirectedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        indegree = new int[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public DirectedGraph(In in) {
        this.V = in.readInt();
        int E = in.readInt();
        adj = (Bag<Integer>[]) new Bag[V];
        indegree = new int[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }

        while (!in.isEmpty()) {
            int v = in.readInt();
            int w = in.readInt();
            this.addEdge(v, w);
        }
    }

    public void addEdge(int v, int w) {
        validate(v);
        validate(w);
        adj[v].add(w);
        indegree[w]++;
        this.E++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        validate(v);
        return adj[v];
    }

    public int outdegree(int v) {
        validate(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validate(v);
        return indegree[v];
    }

    public DirectedGraph reverse(DirectedGraph G) {
        DirectedGraph reverse = new DirectedGraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public void validate(int v) {
        if (v < 0 && v >= V) throw new IllegalArgumentException("");
    }
}
