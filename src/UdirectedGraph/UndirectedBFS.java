package UdirectedGraph;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class UndirectedBFS {
    private boolean[] marked;
    private int count;

    public UndirectedBFS(Graph G, int s) {
        marked = new boolean[G.V()];
        count = 0;
        validateVertex(s);
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            marked[v] = true;
            count++;
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int count() {
        return count;
    }


    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
