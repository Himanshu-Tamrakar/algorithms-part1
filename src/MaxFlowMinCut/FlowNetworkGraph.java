package MaxFlowMinCut;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;

public class FlowNetworkGraph {
    private final int V;
    private int E;
    Bag<FlowEdge>[] adj;

    public FlowNetworkGraph(int V) {
        this.V = V;
        this.E = 0;
//        adj = (FlowEdge[]) new Bag[V];
//
//        for (int v = 0; v < V; v++) {
//            adj[v] = new Bag<>();
//        }
    }
}
