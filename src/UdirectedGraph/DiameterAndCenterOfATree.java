/**
 * 1. DiaMeter
 * Pick any node r.
 * Run BFS/DFS from r, find the farthest node from it → call this node A.
 * Run BFS/DFS again, but this time starting from A, and find the farthest node from it → call this node B.
 * The path A — … — B is the diameter of the tree.
 *
 * DFS give first leaf node and second dfs from first lead node five second leaf node
 *
 * 2. Center of tree
 * Method A: Use the Diameter
 * First compute the diameter endpoints A and B (from the previous algorithm).
 * Record the actual path connecting A and B.
 * The center(s) of the tree are the middle of this diameter path:
 * If the diameter path length is even → 1 center
 * If odd → 2 centers (a "bicenter")
 * This is easy and still linear.
 *
 * Method B: Leaf-Peeling (Topological trimming)
 * This is conceptually nicer:
 * Put all leaves (degree 1 vertices) into a queue.
 * Repeatedly remove all current leaves at the same time:
 * reduce degrees of neighbors
 * any neighbors that become leaves join the queue
 * Stop when 1 or 2 vertices remain → these are the centers.
 * This is similar to finding the core of the tree.
 * Also O(n), because every node/edge is processed once.
 */
package UdirectedGraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class DiameterAndCenterOfATree {
    private boolean[] marked;
    private int[] distTo;
    private int max;
    int leaf;
    int leaf1;
    int leaf2;
    int totalNodes;
    public DiameterAndCenterOfATree(Graph G) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        totalNodes = G.V();
        reset(G);
        distTo[0] = 0;
        dfs(G, 0);

        int firstLeaf = leaf;
        leaf1 = firstLeaf;
        reset(G);
        distTo[firstLeaf] = 0;
        dfs(G, firstLeaf);
        leaf2 = leaf;

        getCenter(G, leaf1, leaf2, G.V());
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;

        for (int w: G.adj(v)) {
            if (!marked[w]) {
                distTo[w] = distTo[v] + 1;
                if (max < distTo[w]) {
                    max = distTo[w];
                    leaf = w;
                }
                dfs(G, w);
            }
        }
    }

    private boolean isLeadNode(Graph G, int v) {
        int count = 0;
        for (int w: G.adj(v)) count++;
        return count  <= 1;

    }

    private void getCenter(Graph G, int leaf1, int leaf2, int V) {
        if (leaf1 == leaf2) { // if diameter is even
            System.out.println("Center Found: " + leaf1);
            return;
        }
        if (V == 2) { // If diamete is odd
            System.out.println("Center Found: " + leaf1 + " : " + leaf2);
            return;
        }
        for (int w: G.adj(leaf1)) {
            if (!isLeadNode(G, w)) leaf1 = w; // only pick non leaf is the trick
            else V--; // removing leaf node
        }
        for (int w: G.adj(leaf2)) {
            if (!isLeadNode(G, w)) leaf2 = w;
            else V--; // removing leaf node.
        }

        getCenter(G, leaf1, leaf2, V);
    }

    public int farthestLeaf() {
        return leaf;
    }

    public int diameter() {
        return this.max;
    }

    private void reset(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            marked[v] = false;
            distTo[v] = Integer.MAX_VALUE;
        }
        max = 0;
    }

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/UdirectedGraph/diameter-center.txt");
        Graph G = new Graph(in);
        DiameterAndCenterOfATree diameterAndCenterOfATree = new DiameterAndCenterOfATree(G);
        System.out.println(diameterAndCenterOfATree.diameter());

    }
}
