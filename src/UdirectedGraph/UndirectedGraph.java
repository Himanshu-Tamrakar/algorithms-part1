package UdirectedGraph;


import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class UndirectedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");


    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public UndirectedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<Integer>();
        }
    }

    public UndirectedGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.readInt();
            if (this.V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
            adj = new Bag[this.V];
            for (int i = 0; i < this.V; i++) {
                adj[i] = new Bag<Integer>();
            }

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                add(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }

    }

    public UndirectedGraph(UndirectedGraph G) {
        this.V = G.V();
        this.E = G.E();
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");

        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original because bag add new element to front
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }



    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public void add(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public int degree(UndirectedGraph G, int v) {
        return G.degree(v);
    }

    public int maxDegree(UndirectedGraph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) max = degree(G, v);
        }
        return max;
    }

    public double averageDegree(UndirectedGraph G) {
        return 2.0 * G.E() / G.V();
    }

    public int numberOfSeldLoop(UndirectedGraph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w: G.adj(v)) {
                if (v == w) count++;
            }
        }
        return count / 2;
    }


    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/tinyG.txt");
        UndirectedGraph G = new UndirectedGraph(in);
        StdOut.println(G);
    }


}
