package UdirectedGraph;

/******************************************************************************
 *  Compilation:  javac DegreesOfSeparation.java
 *  Execution:    java DegreesOfSeparation filename delimiter source
 *  Dependencies: SymbolGraph.java Graph.java BreadthFirstPaths.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/routes.txt
 *                https://algs4.cs.princeton.edu/41graph/movies.txt
 *
 *
 *  %  java DegreesOfSeparation routes.txt " " "JFK"
 *  LAS
 *     JFK
 *     ORD
 *     DEN
 *     LAS
 *  DFW
 *     JFK
 *     ORD
 *     DFW
 *  EWR
 *     Not in database.
 *
 *  % java DegreesOfSeparation movies.txt "/" "Bacon, Kevin"
 *  Kidman, Nicole
 *     Bacon, Kevin
 *     Woodsman, The (2004)
 *     Grier, David Alan
 *     Bewitched (2005)
 *     Kidman, Nicole
 *  Grant, Cary
 *     Bacon, Kevin
 *     Planes, Trains & Automobiles (1987)
 *     Martin, Steve (I)
 *     Dead Men Don't Wear Plaid (1982)
 *     Grant, Cary
 *
 *  % java DegreesOfSeparation movies.txt "/" "Animal House (1978)"
 *  Titanic (1997)
 *     Animal House (1978)
 *     Allen, Karen (I)
 *     Raiders of the Lost Ark (1981)
 *     Taylor, Rocky (I)
 *     Titanic (1997)
 *  To Catch a Thief (1955)
 *     Animal House (1978)
 *     Vernon, John (I)
 *     Topaz (1969)
 *     Hitchcock, Alfred (I)
 *     To Catch a Thief (1955)
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.*;

public class UndirectedDegreeOfSeparation {
    public static void main(String[] args) {
        String filename  = "/home/decimal/personal/algorithms/temp/src/UdirectedGraph/movies.txt";
        String delimiter = "/";
        String source = "Bacon, Kevin";
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph G = sg.graph();
        int s = sg.indexOf(source);

        BreadthFirstPaths bfp = new BreadthFirstPaths(G, s);
        while (!StdIn.isEmpty()) {
            String sink = StdIn.readLine();
            if(sg.contains(sink)) {
                int t = sg.indexOf(sink);
                if (bfp.hasPathTo(t)) {
                    for (int v: bfp.pathTo(t)) {
                        StdOut.println("   " + sg.nameOf(v));
                    }
                } else {
                    StdOut.println("Not connected");
                }
            } else {
                StdOut.println("   Not in database.");
            }
        }

    }
}
