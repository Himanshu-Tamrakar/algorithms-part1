package UdirectedGraph;

import edu.princeton.cs.algs4.*;

public class ConnectedComponent {
    private boolean[] marked;
    private int[] id;
    private int count;

    public ConnectedComponent(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;
        for (int i = 0; i < G.V(); i++) {
            id[i] = -1;
        }

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }


    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w: G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }


    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public int count() {
        return count;
    }

    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/tinyG.txt");
        Graph G = new Graph(in);
        ConnectedComponent cc = new ConnectedComponent(G);

        // number of connected components
        int m = cc.count();
        StdOut.println(m + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }



}
