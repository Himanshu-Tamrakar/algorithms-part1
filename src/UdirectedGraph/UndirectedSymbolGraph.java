package UdirectedGraph;

import edu.princeton.cs.algs4.*;

import java.util.HashMap;

public class UndirectedSymbolGraph {
    ST<String, Integer> st = new ST<>();
    String[] keys;
    Graph G;
    public UndirectedSymbolGraph(String filename, String delimeter) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/" + filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimeter);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i])) st.put(a[i], st.size());
            }
        }
        keys = new String[st.size()];
        for (String name: st.keys()) {
            keys[st.get(name)] = name;
        }

        // build the graph
        G = new Graph(st.size());
        in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/" + filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimeter);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                G.addEdge(v, w);
            }
        }


    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    @Deprecated
    public int index(String s) {
        return st.get(s);
    }

    public int indexOf(String s) {
        return st.get(s);
    }

    @Deprecated
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    @Deprecated
    public Graph G() {
        return G;
    }

    public Graph graph() {
        return G;
    }

    private void validateVertex(int v) {
        int V = G.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    public static void main(String[] args) {
        String filename  = "routes.txt";
        String delimiter = " ";
//        String filename  = "movies.txt";
//        String delimiter = "/";
        UndirectedSymbolGraph sg = new UndirectedSymbolGraph(filename, delimiter);
        Graph graph = sg.graph();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            if (sg.contains(source)) {
                int s = sg.index(source);
                for (int v : graph.adj(s)) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            }
            else {
                StdOut.println("input not contain '" + source + "'");
            }
        }

    }
}
