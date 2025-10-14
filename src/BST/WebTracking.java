package BST;

import edu.princeton.cs.algs4.StdOut;

import javax.swing.text.Style;
import java.util.HashMap;
import java.util.Map;

public class WebTracking {
    Map<String, Map<String, Integer>> table;

    public WebTracking() {
        table = new HashMap<>();
    }

    public void visit(String user, String website) {
        if (!table.containsKey(user)) {
            table.put(user, new HashMap<String, Integer>());
        }
        Map<String, Integer> visits = table.get(user);
        if (!visits.containsKey(website)) visits.put(website, 1);
        else visits.put(website, visits.get(website) + 1);
    }

    public int getVisitCount(String user, String website) {
        if (!table.containsKey(user)) return 0;
        Map<String, Integer> visits = table.get(user);
        if (!visits.containsKey(website)) return 0;
        return visits.get(website);
    }

    public static void main(String[] args) {
        WebTracking webTracking = new WebTracking();
        String u1 = "Himanshu";
        String u2 = "Aman";
        String u3 = "Anshul";
        String u4 = "Anoop";

        String w1 = "https://google.com";
        String w2 = "https://atlasian.com";
        String w3 = "https://github.com";

        webTracking.visit(u1, w3);
        webTracking.visit(u1, w2);
        webTracking.visit(u1, w2);

        StdOut.println("User : " + u1 + " visisted website " + w1 + " : " + webTracking.getVisitCount(u1, w1));
        StdOut.println("User : " + u1 + " visisted website " + w2 + " : " + webTracking.getVisitCount(u1, w2));
        StdOut.println("User : " + u1 + " visisted website " + w3 + " : " + webTracking.getVisitCount(u1, w3));


    }
}
