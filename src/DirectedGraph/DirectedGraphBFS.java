package DirectedGraph;

import edu.princeton.cs.algs4.*;

public class DirectedGraphBFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private int count;

    public DirectedGraphBFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()] ;
        count = 0;
        bfs(G, s);
    }

    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        edgeTo[s] = s;
        distTo[s] = 0;
        marked[s] = true;

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }

    private void bfs(Digraph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<>();
        for (int v: sources) {
            marked[v] = true;
            edgeTo[v] = 0;
            q.enqueue(v);
        }

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }


}
