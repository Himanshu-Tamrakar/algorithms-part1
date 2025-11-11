package UdirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class UdirectedBipartite {
    private boolean[] marked;
    private boolean[] color;
    private int[] edgeTo;
    Stack<Integer> cycle = null;
    private boolean isBipartite = true;
    public UdirectedBipartite(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) dfs(G, v);
        }

    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w: G.adj(v)) {
            if (cycle != null) return;

            if (!marked[w]) {
                edgeTo[w] = v;
                color[w] = !color[v];
                dfs(G, w);
            } else if (color[w] == color[v]) {
                isBipartite = false;
                cycle = new Stack<>();
                cycle.push(w);
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);

            }
        }
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public boolean color(int v) {
        validateVertex(v);
        if (!isBipartite)
            throw new UnsupportedOperationException("graph is not bipartite");
        return color[v];
    }

    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private class Edge {
        int v;
        int w;
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        public int any() {
            return this.v;
        }
        public int other(int vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new IllegalArgumentException("");
        }
    }

    // Different logic
    public boolean isBipartiteAnotherWay(Graph G) {
        boolean[] marked = new boolean[G.V()];
        boolean[] color = new boolean[G.V()];
        Queue<Integer> q = new Queue<>();
        int s = 0;
        q.enqueue(s);
        // Maintain color for each adjecent vertext
        while (!q.isEmpty()) {
            int v = q.dequeue();
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[v] = true;
                    color[w] = !color[v];
                    q.enqueue(w);
                }
            }
        }

        Queue<Edge> edges = new Queue<>();
        // Add edge exactle once
        for (int v = 0; v < G.V(); v++) {
            int selfLoop = 0;
            for (int w: G.adj(v)) {
                if (w == v) {
                    if (selfLoop % 2 == 0) {
                        Edge e = new Edge(v, w);
                        edges.enqueue(e);
                    }
                    selfLoop++;
                } else if (v < w) {
                    Edge e = new Edge(v, w);
                    edges.enqueue(e);
                }
            }

        }

        // Check each edge vertext has different color
        for (Edge e: edges) {
            int v = e.any();
            int w = e.other(v);
            if (color[v] == color[w]) return false;
        }

        return true;

    }

    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(0, 2);
        UdirectedBipartite udirectedBipartite = new UdirectedBipartite(G);
        System.out.println(udirectedBipartite.isBipartite());
        System.out.println(udirectedBipartite.isBipartiteAnotherWay(G));

        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/tinyG.txt");
        Graph G1 = new Graph(in);
        UdirectedBipartite udirectedBipartite1 = new UdirectedBipartite(G1);
        System.out.println(udirectedBipartite1.isBipartite());
        System.out.println(udirectedBipartite1.isBipartiteAnotherWay(G1));


        in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/bipartine.txt");
        G1 = new Graph(in);
        udirectedBipartite1 = new UdirectedBipartite(G1);
        System.out.println(udirectedBipartite1.isBipartite());
        System.out.println(udirectedBipartite1.isBipartiteAnotherWay(G1));
    }
 }
