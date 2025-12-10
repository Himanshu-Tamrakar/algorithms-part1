package ShortestPath;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

public class SPInterviewQuestions {

    double[] distTo;
    DirectedEdge[] edgeTo;

    /**
     * Monotonic shortest path. Given an edge-weighted digraph G, design an Elog(E) algorithm to find a monotonic shortest path from s to every other vertex.
     * A path is monotonic if the sequence of edge weights along the path are either strictly increasing or strictly decreasing.
     */
    public void monotonicPath(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        MinPQ<DirectedEdge> pq = new MinPQ<>(G.E());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;

        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e: G.adj(v)) {
                pq.insert(e);
            }
        }

        while (!pq.isEmpty()) {
            DirectedEdge e = pq.delMin();
            relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();

        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public void secondShortestPath(EdgeWeightedDigraph G, int s) {}

    public void shortestPathWithOneSkippebleEdge(EdgeWeightedDigraph G, int s) {}
}
