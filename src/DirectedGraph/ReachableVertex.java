/**
 * Question 3 Reachable vertex.
 * DAG: Design a linear-time algorithm to determine whether a DAG has a vertex that is reachable from every other vertex, and if so, find one.
 * Digraph: Design a linear-time algorithm to determine whether a digraph has a vertex that is reachable from every other vertex, and if so, find one.
 */
package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.KosarajuSharirSCC;
import edu.princeton.cs.algs4.Queue;

public class ReachableVertex {
    public static int findVInDAG(Digraph G) {
        int count = 0;
        int V = -1;
        // If there is exactly one vertex whose outdegree is 0, it means all other vertices are eventually danded to this perticular vertex
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) {
                count++;
                V = v;
            }
        }
        return V;
    }

    public static int findVInDigraph(Digraph G) {
        int V = -1;
        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);
        Digraph digraph = new Digraph(scc.count());
        for (int v = 0; v < G.V(); v++) {
            for (int w: G.adj(v)) {
                if (!scc.stronglyConnected(v, w)) { // If vertex v and w are belong to different connect component.
                    digraph.addEdge(scc.id(v), scc.id(w));
                }
            }
        }

        int id = findVInDAG(digraph);

        for (int v = 0; v < G.V(); v++) {
            if (scc.id(v) == id) {
                V = v;
                break;

            }
        }
        return V;
    }

    /**
     * in a digraph let say we have two stong component
     * 0->1, 1->2, 2->3, 3->0  and 4->5, 5->6, 6->4
     * and extra end which goes from one connected component to another connected component let say
     * 2 -> 4
     *
     * here digraph in findVInDigraph method we create a new DAG and add only 2 -> 4 edge as it links to other component.
     * -------------------------------|        |------------------------------I
     * 0 ---> 1 ---> 2 ----> 3 ---> 0 | -----> | 4--->5 ---> 6 ----> 4        I
     * ^------------------------------| (2->4) |------------------------------I
     *
     *
     *
     */


}
