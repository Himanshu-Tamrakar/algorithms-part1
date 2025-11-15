package DirectedGraph.WordNet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final Digraph G;
    private final HashMap<String, ArrayList<Integer>> nouns;
    private final HashMap<Integer, ArrayList<String>> ids;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("argument is null");
        }
        nouns = new HashMap<>();
        ids = new HashMap<>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] synsetRow = in.readLine().split(",");
            String key = synsetRow[0];
            String val = synsetRow[1];
            String[] vals = val.split(" ");

            ArrayList<String> nounList = new ArrayList<>();
            for (String s : vals) {
                nounList.add(s);

                ArrayList<Integer> ints = nouns.get(s);
                if (ints == null) {
                    ints = new ArrayList<>();
                    nouns.put(s, ints);
                }
                ints.add(Integer.parseInt(key));
            }
            ids.put(Integer.parseInt(key), nounList);
        }

        G = new Digraph(nouns.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] strings = in.readLine().split(",");
            int v = Integer.parseInt(strings[0]);
            for (int i = 1; i < strings.length; i++) {
                int w = Integer.parseInt(strings[i]);
                G.addEdge(v, w);
            }
        }

        DirectedCycle d = new DirectedCycle(G);
        if (d.hasCycle()) {
            throw new IllegalArgumentException("Is not a DAG");
        } else if (!rootedDAG()) {
            throw new IllegalArgumentException("Is not a rooted DAG");
        }

        sap = new SAP(G);
    }

    private boolean rootedDAG() {
        int count = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.indegree(i) != 0 && G.outdegree(i) == 0) {
                count++;
            }
        }
        return count == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("is not wordnet noun");
        }
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("is not wordnet noun");
        }
        int s = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        ArrayList<String> res = ids.get(s);
        String outcome = "";
        for (String t : res) {
            outcome = outcome.concat(t + " ");
        }
        return outcome;
    }

}
