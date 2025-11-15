package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class TopologicalOrderingX {
    private boolean[] marked;
    private int[] indegree;
    private Queue<Integer> order;
    private int[] rank;

    public TopologicalOrderingX(Digraph G) {
        marked = new boolean[G.V()];
        indegree = new int[G.V()];
        rank = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            indegree[v] = G.indegree(v);
        }
        order = new Queue<>();

        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) queue.enqueue(v);
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            rank[v] = count++;
            order.enqueue(v);
            for (int w: G.adj(v)) {
                indegree[w]--;
                if (indegree[w] == 0) queue.enqueue(w);
            }
        }

        // if cycle count will not be G.V(), it will be more the G.V()
        if (count != G.V()) {
            order = null;
        }

    }
}
