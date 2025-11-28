package MST;




import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST {
    private final Bag<Edge> edges;
    private double weight;
    IndexMinPQ<Double> pq;
    private Edge[] edgeTo;
    private boolean[] marked;
    private double[] distTo;
    public PrimMST(EdgeWeightedGraph G) {
        int V = G.V();
        edges = new Bag<>();
        pq = new IndexMinPQ<>(V);
        edgeTo = new Edge[V];
        marked = new boolean[V];
        distTo = new double[V];
        for (int v = 0; v < V; v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0.0;



    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;

        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (distTo[w] > e.weight()) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, e.weight());
                else pq.insert(w, weight);
            }
        }
    }


}
