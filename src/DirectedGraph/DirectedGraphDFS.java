package DirectedGraph;

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class DirectedGraphDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private int count;
    public DirectedGraphDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        count = 0;
        edgeTo[s] = s;
        dfs(G, s);
    }

    public DirectedGraphDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w: G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                count++;
                dfs(G, w);
            }
        }
    }

    private void iterativeDFS(Digraph G, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        marked[s] = true;
        edgeTo[s] = s;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int w: G.adj(v)) {
                if (!marked[w])  {
                    marked[w] = true;
                    edgeTo[w] = v;
                    stack.push(w);
                }
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }

    private Iterable<Integer> pathTo(int v) {
        Stack<Integer> path = new Stack<>();

        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }


}
