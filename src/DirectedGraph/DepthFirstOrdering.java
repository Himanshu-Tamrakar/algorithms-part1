package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class DepthFirstOrdering {
    private Queue<Integer> preorder;
    private int[] pre; // maintain preorder of number of vertex v
    private int preOrderCount = 0;
    private Queue<Integer> postorder;
    private int[] post; // maintain post order of number of vertex v
    private int postOrderCount = 0;
    private boolean[] marked;

    public DepthFirstOrdering(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        preorder = new Queue<>();
        postorder = new Queue<>();

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        preorder.enqueue(v);
        pre[v] = preOrderCount++;
        for (int w: G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
        post[v] = postOrderCount++;


    }

}
