package DirectedGraph.WordNet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("argument is null");
        }
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validate(v);
        validate(w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return commonShortextAncesterAndLength(bfsV, bfsW)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v);
        validate(w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return commonShortextAncesterAndLength(bfsV, bfsW)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);

        int c = 0, r = 0;
        for (int ignored : v) {
            c++;
        }
        for (int ignored : w) {
            r++;
        }

        if (c == 0 || r == 0) {
            return -1;
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return commonShortextAncesterAndLength(bfsV, bfsW)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);

        int c = 0, r = 0;
        for (int ignored : v) {
            c++;
        }
        for (int ignored : w) {
            r++;
        }

        if (c == 0 || r == 0) {
            return -1;
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        return commonShortextAncesterAndLength(bfsV, bfsW)[1];
    }

    private void validate(int vertex) {
        int v = digraph.V();
        if (vertex < 0 || vertex >= v) {
            throw new IllegalArgumentException("not in range");
        }
    }

    private void validate(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Integer val : v) {
            if (val == null) {
                throw new IllegalArgumentException("value inside iterable is invalid");
            }
        }
    }




    private int[] commonShortextAncesterAndLength(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int[] res = new int[2];
        int len = -1;
        int p = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int lv = bfsV.distTo(i);
                int wv = bfsW.distTo(i);
                if (lv + wv < len || len == -1) {
                    len = lv + wv;
                    p = i;
                }
            }
        }
        res[0] = len;
        res[1] = p;
        return res;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
