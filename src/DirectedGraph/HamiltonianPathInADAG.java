// Check input from video lecture which is not a hamiltonion path
package DirectedGraph;

import edu.princeton.cs.algs4.*;

public class HamiltonianPathInADAG {
        private Stack<Integer> hamiltonianPath = null;
        private boolean hasHamiltonianPath = false;

        public HamiltonianPathInADAG(Digraph G) {

            Topological topological = new Topological(G);
            if (!topological.hasOrder()) {
                return;
            }

            Iterable<Integer> order = topological.order();
            Integer prev = null;
            this.hasHamiltonianPath = true;
            this.hamiltonianPath = new Stack<>();

            // Iterate through the topological order and check for consecutive edges.
            for (Integer v : order) {
                // Push the current vertex onto the potential path
                this.hamiltonianPath.push(v);

                if (prev != null) {
                    // Check if there is an edge from the previous vertex to the current vertex
                    boolean edgeExists = false;
                    for (int neighbor : G.adj(prev)) {
                        if (neighbor == v) {
                            edgeExists = true;
                            break;
                        }
                    }

                    // If any consecutive pair is not connected by a direct edge, no Hamiltonian path exists.
                    if (!edgeExists) {
                        this.hasHamiltonianPath = false;
                        this.hamiltonianPath = null; // Clear the path as it's not valid
                        return;
                    }
                }
                prev = v;
            }

        }

        public boolean hasHamiltonianPath() {
            return hasHamiltonianPath;
        }

        public Iterable<Integer> getHamiltonianPath() {
            return hamiltonianPath;
        }

        public static void main(String[] args) {
            Digraph G = new Digraph(7);
            G.addEdge(0, 1);
            G.addEdge(0, 2);
            G.addEdge(0, 5);

            G.addEdge(1, 4);

            G.addEdge(3, 2);
            G.addEdge(3, 4);
            G.addEdge(3, 5);
            G.addEdge(3, 6);

            G.addEdge(5, 2);
            G.addEdge(6, 0);
            G.addEdge(6, 4);


            HamiltonianPathInADAG hamiltonianPathInADAG = new HamiltonianPathInADAG(G);

            System.out.println(hamiltonianPathInADAG);



        }
    }
