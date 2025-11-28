package MST;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;

public class LazyPrimMST {
    private final Bag<Edge> edges;
    private double weight;
    private boolean[] marked;
    private final MinPQ<Edge> pq;
    public LazyPrimMST(EdgeWeightedGraph G) {
        int V = G.V();
        marked = new boolean[V];
        edges = new Bag<>();
        weight = 0.0;
        pq = new MinPQ<>();

        scan( G,0);
        while (!pq.isEmpty() && edges.size() < V) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            edges.add(e);
            weight += e.weight();
            if(!marked[v]) scan(G, v);
            if(!marked[w]) scan(G, w);
        }

    }

    private void scan(EdgeWeightedGraph G, int s) {
        marked[s] = true;
        for (Edge e: G.adj(s)) {
            int w = e.other(s);
            if (!marked[w]) pq.insert(e);
        }
    }

    public Iterable<Edge> edges() {
        return edges;
    }

    public double weight() {
        return weight;
    }
}
