package MaxFlowMinCut;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.Queue;

public class FordFolkersonAlgo {
    private boolean[] markerd;
    private FlowEdge[] edgeTo;
    private double value;
    public FordFolkersonAlgo(FlowNetwork G, int s, int t) {

        while (hasAugumentedPath(G, s, t)) {
            double bottle = Double.MAX_VALUE;

            // find bottleneck capacity
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(edgeTo[v].other(v)));
            }

            // augument the flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }

    }

    private boolean hasAugumentedPath(FlowNetwork G, int s, int t) {
        markerd = new boolean[G.V()];
        edgeTo  = new FlowEdge[G.V()];

        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        markerd[s] = true;

        while (!q.isEmpty() && !markerd[t]) {
            int v = q.dequeue();

            for (FlowEdge edge: G.adj(v)) {
                int w = edge.other(v);

                if (edge.residualCapacityTo(w) > 0) {
                    if (!markerd[w]) {
                        markerd[w] = true;
                        edgeTo[w] = edge;
                        q.enqueue(w);
                    }
                }
            }
        }

        return markerd[t];
    }

}
