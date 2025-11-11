package UdirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        count = 0;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w: G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    private void dfsIterative(Graph G, int s) {
        Stack<Integer> stack = new Stack<>();
        marked[s] = true;
        stack.push(s);

        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                    marked[w] = true;
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

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/tinyG.txt");
        Graph G = new Graph(in);
        int s = 0;
        DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }

        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else                         StdOut.println("connected");
    }
}
