package SubStringSearch;

public class RobinKarp {

    private int R = 10;
    private final int Q = 997;
    private int RM;
    private String pat;
    private long patHash;
    private int M;

    public RobinKarp(String pat) {
        this.pat = pat;      // save pattern (needed only for Las Vegas)
        R = 256;
        M = pat.length();

        // precompute R^(m-1) % q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= M-1; i++)
            RM = (R * RM) % Q;
        patHash = hash(pat, M);
        System.out.println(patHash);
    }
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    public static void main(String[] args) {
        String pat = "26535";
        RobinKarp robinKarp = new RobinKarp(pat);
    }
}
