/**
 * Question 1 Shortest directed cycle.
 * Given a digraph 𝐺 GG, design an efficient algorithm to find a directed cycle with the minimum number of edges (or report that the graph is acyclic).
 * The running time of your algorithm should be at most proportional to 𝑉 ( 𝐸 + 𝑉 ) V(E+V)V,  (E +, V )
 * and use space proportional to 𝐸 + 𝑉 where 𝑉 is the number of vertices and 𝐸 is the number of edges.
 *
 * We can not do onstack[w] because we are check cycle from s. 0 -> 1 -> 2 -> 3 -> 1
 * As we do bfs from 0 it should have cycle ends in 0 not at others
 */
package DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class ShortestDirectedCycle {
    private boolean[] marked;
    private int[] distTo;
    private int[] edgeTo;
    private boolean[] onstack;
    private Stack<Integer> cycle = null;
    public Stack<Integer> minCycle = null;
    private int minLen = Integer.MAX_VALUE;
    public ShortestDirectedCycle(Digraph G) {
        for (int v = 0; v < G.V(); v++) {
            bfs(G, v);
        }
    }

    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<>();
        int[] distTo = new int[G.V()];
        int[] edgeTo = new int[G.V()];
        boolean[] marked = new boolean[G.V()];

        for (int i = 0; i < G.V(); i++) distTo[i] = -1;

        q.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;

        while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        marked[w] = true;
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        q.enqueue(w);
                    }
                    else if (w == s) {
                        int cycleLength = distTo[v] + 1;

                        if (cycleLength < minLen) {
                            minLen = cycleLength;
                            Stack<Integer> cycle = new Stack<>();
                            cycle.push(s);
                            for (int x = v; x != s; x = edgeTo[x]) {
                                cycle.push(x);
                            }
                            cycle.push(s);
                            minCycle = cycle;
                        }
                    }
                }
        }
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(6);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 0);
        G.addEdge(2, 4);
        G.addEdge(4, 5);
        G.addEdge(5, 2);

        ShortestDirectedCycle shortestDirectedCycle = new ShortestDirectedCycle(G);
        System.out.println(shortestDirectedCycle);
    }
}
