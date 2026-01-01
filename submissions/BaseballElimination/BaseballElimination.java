

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private final int[] wins;
    private final int[] loses;
    private final int[] remainings;
    private final String[] teamNames;
    private Map<String, Integer> teams;
    private final int n;
    private final int[][] games;
    private int totalRemainingGames;
    private List<String> list;
    private int maxWin;
    private int maxWinningTeamId;

    public BaseballElimination(String filename) {
        if (filename == null) throw new IllegalArgumentException("argument is null");
        In in = new In(filename);
        n = in.readInt();
        teams = new HashMap<>();
        teamNames = new String[n];
        wins = new int[n];
        loses = new int[n];
        remainings = new int[n];
        games = new int[n][n];

        int i = 0;
        maxWin = Integer.MIN_VALUE;
        while (!in.isEmpty()) {
            String teamName = in.readString();
            int win = in.readInt();
            int loose = in.readInt();
            int remains = in.readInt();
            teams.put(teamName, i);
            teamNames[i] = teamName;
            wins[i] = win;
            loses[i] = loose;
            remainings[i] = remains;

            for (int j = 0; j < n; j++) {
                games[i][j] = in.readInt();
            }

            if (wins[i] > maxWin) {
                maxWin = wins[i];
                maxWinningTeamId = i;
            }

            i++;

        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validate(team);
        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validate(team);
        return loses[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validate(team);
        return remainings[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        return games[teams.get(team1)][teams.get(team2)];
    }

    public boolean isEliminated(String team) {
        validate(team);
        if (trivialElimination(team)) {
            return true;
        }
        return nonTrivialElimination(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validate(team);
        if (!isEliminated(team)) {
            return null;
        }
        if (trivialElimination(team)) {
            list = new LinkedList<>();
            list.add(teamNames[maxWinningTeamId]);
        } else {
            nonTrivialElimination(team);
        }
        return list;
    }

    private boolean trivialElimination(String team) {
        return wins[teams.get(team)] + remainings[teams.get(team)] < maxWin;
    }

    private boolean nonTrivialElimination(String team) {
        int size = (n * (n - 1)) / 2;
        FlowNetwork flowNetwork = new FlowNetwork(size + 2); // 2 is for artificial source and target vertex
        int s = size;
        int t = size + 1;

        // Building Graph from source to game vertices to team vertices to target vertex
        int startGameVertices = n-1; // Game vertices starting from
        totalRemainingGames = 0;
        for (int i = 0; i < n; i++) {
            if (i == teams.get(team)) continue; // exclude for x team from row

            int firstTeamId = i < teams.get(team) ? i : i-1;

            for (int j = i+1; j < n; j++) {
                if (j == teams.get(team)) continue; // exclude for x team from column
                int secondTeamId = j < teams.get(team) ? j : j-1;
                // Start vertex to game vertex
                flowNetwork.addEdge(new FlowEdge(s, startGameVertices, games[i][j]));
                totalRemainingGames += games[i][j];

                // game vertices to team vertices
                flowNetwork.addEdge(new FlowEdge(startGameVertices, firstTeamId, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(startGameVertices++, secondTeamId, Double.POSITIVE_INFINITY));

            }

            int capacityToTarget = wins[teams.get(team)] + remainings[teams.get(team)] - wins[i];
            if (capacityToTarget < 0) continue;
            flowNetwork.addEdge(new FlowEdge(firstTeamId, t, capacityToTarget));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);

        // creating R
        list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (i == teams.get(team)) {
                continue;
            }
            int firstTeamId = i;
            if (i > teams.get(team)) {
                firstTeamId = i - 1;
            }
            if (fordFulkerson.inCut(firstTeamId)) {
                list.add(teamNames[i]);
            }
        }

        return (int) fordFulkerson.value() != totalRemainingGames;
    }


    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (String str : teams.keySet()) {
            if (name.equals(str)) {
                return;
            }
        }
        throw new IllegalArgumentException("argument is illegal");
    }

    public static void main(String[] args) {
//        BaseballElimination division = new BaseballElimination("https://coursera.cs.princeton.edu/algs4/assignments/baseball/files/teams5.txt");
        BaseballElimination division = new BaseballElimination("/home/decimal/personal/algorithms/temp/src/MaxFlowMinCut/BaseballElimination/teams5.txt");

//        for (String team : division.teams()) {
//            if (division.isEliminated(team)) {
//                StdOut.print(team + " is eliminated by the subset R = { ");
//                for (String t : division.certificateOfElimination(team)) {
//                    StdOut.print(t + " ");
//                }
//                StdOut.println("}");
//            } else {
//                StdOut.println(team + " is not eliminated");
//            }
//        }
//        System.out.println(division.teams());
//        System.out.println(division.against("Toronto", "Detroit"));
//        System.out.println(division.wins("Boston"));
//        System.out.println(division.losses("Boston"));
//        System.out.println(division.numberOfTeams());
//        System.out.println(division.remaining("Baltimore"));

        System.out.println(division.isEliminated("Baltimore"));
        System.out.println(division.certificateOfElimination("Baltimore"));
    }
}
