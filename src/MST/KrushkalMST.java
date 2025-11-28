package MST;


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Edge;

public class KrushkalMST {
    private Bag<Edge> edges;
    private double weight;
    public KrushkalMST(EdgeWeightedGraph G) {
        int V = G.V();
        weight = 0.0;
        edges = new Bag<>();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(V);
        MinPQ<Edge> pq = new MinPQ<>();

        for (Edge e: G.edges()) {
            pq.insert(e);
        }

        while (!pq.isEmpty() && edges.size() < V) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                edges.add(e);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return edges;
    }

    public double weight() {
        return weight;
    }
}
