package MST;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class MSTInterviewQuestion {

    private boolean[] marked;
    private Edge edge;

    public void MinimumBottleneckSpanningTree() {
        // A BMST is a spanning tree whose bottleneck (maximum edge weight) is as small as possible among ALL spanning trees.
        // A-B weight 2
        // B-C weight 3
        // A-C weight 10

        // Possible trees can be with {2, 3} and {2, 10} and {3, 10}
        // Tree 1 is better because its worst edge (3) is smaller than 10.

        // MST is solution for this because MST select minimum weight edge to form minimum spanning tree
    }

    // Is there a path from u to v where every edge has weight < w(e)?
    public void isAnEdgeInMST(EdgeWeightedGraph G, Edge edge) {
        this.edge = edge;
        marked = new boolean[G.V()];
        int v = edge.either();
        dfs(G, edge.either());
        if (marked[edge.other(v)]) {
            System.out.println("Edge " + edge + " can not be in MST");
        } else {
            System.out.println("Edge " + edge + " will be in MST");
        }
    }

    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;

        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (!marked[w] && e.weight() < edge.weight()) dfs(G, w);
        }
    }

    public void minimumWeightFeedbackEdgeSet() {
        // Feedback = edge participating in a cycle
        // Edge set = group of edges
        // Minimum weight = choose the set with smallest total weight
        // Goal: remove these edges so the graph becomes acyclic (a forest)

        // Non-MST edges = Minimum-weight feedback edge set
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(4);
        Edge e1 = new Edge(0, 1, 1);
        Edge e2 = new Edge(0, 2, 1.5);
        Edge e3 = new Edge(0, 3, 2.5);
        Edge e4 = new Edge(1, 2, 1);
        Edge e5 = new Edge(2, 3, 1.2);
        G.addEdge(e1);
        G.addEdge(e2);
        G.addEdge(e3);
        G.addEdge(e4);
        G.addEdge(e5);

        MSTInterviewQuestion mstInterviewQuestion = new MSTInterviewQuestion();
        mstInterviewQuestion.isAnEdgeInMST(G, e2);
        mstInterviewQuestion.isAnEdgeInMST(G, e4);

    }
}
