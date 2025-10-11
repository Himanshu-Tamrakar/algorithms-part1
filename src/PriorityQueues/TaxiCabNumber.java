package PriorityQueues;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.HashMap;

public class TaxiCabNumber {
//    public static int[] numbers = { 1729, 4104, 13832 };
    public static int[] numbers = { 1729, 4104, 13832, 20683, 32832, 39312, 40033, 46683, 64232, 65728, 110656, 110808, 134379, 149389, 165464, 171288, 195841, 216027, 216125, 262656, 314496, 320264, 327763, 373464, 402597, 439101, 443889, 513000, 513856, 515375, 525824, 558441, 593047, 684019, 704977, 805688, 842751, 885248, 886464, 920673, 955016, 984067, 994688, 1009736, 1016496, 1061424, 1073375, 1075032, 1080891, 1092728 };

    public static void calculateWithTimeN4(long max) {
        long a3 = 0;
        for(long a = 1; (a3 = a * a * a) < max; a++) {
            long b3 = 0;
            for(long b = a; (b3 = b * b * b) <= max - a3; b++) {
                long ab3 = a3 + b3;
                long c3 = 0;
                for(long c = a; (c3 = c * c * c) < ab3; c++) {
                    long d3 = 0;
                    for(long d = c; (d3 = d * d * d) <= ab3 - c3; d++) {
                        if(ab3 == c3 + d3)
                            System.out.println(ab3 + " = " + a + "³ + " + b + "³ = " + c + "³ + " + d + "³");
                    }
                }
            }
        }
    }

    public static void calculateWithTimeN3(long max) {
        long a3 = 0;
        for(long a = 1; (a3 = a * a * a) < max; a++) {
            long b3 = 0;
            for(long b = a; (b3 = b * b * b) <= max - a3; b++) {
                long ab3 = a3 + b3;
                long c3 = 0;
                for(long c = a; (c3 = c * c * c) < ab3; c++) {
                    double d3 = ab3 - c3;
                    long d = (long)Math.cbrt(d3);
                    if(d >= c && d != a && d != b && d3 == d * d * d)
                        System.out.println(ab3 + " = " + a + "³ + " + b + "³ = " + c + "³ + " + d + "³");
                }
            }
        }
    }

    // Version 1: time complexity O(n2log(n)), Space complexity O(n2)
    public static void calculateV1(long max) {
        HashMap<Long, long[]> table = new HashMap<>();

        long a3 = 0;
        for (int a = 1; (a3 = (long)a * a * a) < max; a++) {
            long b3 = 0;
            for (int b = a; (b3 = (long)b * b * b) <= max - a3; b++) {
                long ab3 = a3 + b3;
                long[] presentAB3 = table.get(ab3);
                if (presentAB3 == null) {
                    table.put(ab3, new long[] { a, b });
                } else {
                    System.out.println(ab3 + " = " + a + "³ + " + b + "³ = " + presentAB3[0] + "³ + " + presentAB3[1] + "³");
                }

            }
        }
    }

    public static class SumOfCubes implements Comparable<SumOfCubes> {
        private long sum;
        private int i;
        private int j;

        public SumOfCubes(int i, int j) {
            this.i = i;
            this.j = j;
            this.sum = (long) i * i * i + (long) j * j * j;
        }

        public long getSum() {
            return sum;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        // Compare SumOfCubes objects based on their sum.
        @Override
        public int compareTo(SumOfCubes other) {
            return Long.compare(this.sum, other.sum);
        }
    }

    // Version 1: time complexity O(n2log(n)), Space complexity O(n)
    public static void calculateV2(long max) {
       MinPQ<SumOfCubes> pq = new MinPQ<>();
        long a3 = 0;
        for (int i = 1; (a3 = (long)i * i * i + 1) < max; i++) {
            pq.insert(new SumOfCubes(i, 1));
        }

        long previousSum = -1;
        SumOfCubes previousEntry = null;
        while (!pq.isEmpty()) {
            SumOfCubes currentEntry = pq.delMin();
            if (currentEntry.getSum() == previousSum) {
                System.out.println(currentEntry.getSum() + " = " + previousEntry.getI() + "^3 + " + previousEntry.getJ() + "^3 = " + currentEntry.getI() + "^3 + " + currentEntry.getJ() + "^3");
            }

            previousSum = currentEntry.getSum();
            previousEntry = currentEntry;
            int i = currentEntry.getI();
            int j = currentEntry.getJ() + 1;
            if ((i * i * i) + (j * j * j) <= max) {
                pq.insert(new SumOfCubes(currentEntry.getI(), currentEntry.getJ() + 1));
            }
        }
    }

    public static void main(String[] args) {
//        for (int taxinumber : numbers) {
//            long start_time = System.nanoTime();
//            TaxiCabNumber.calculateV1(taxinumber);
//            long elapsedTime = (System.nanoTime() - start_time);
//            System.out.println("Time Taken to Calculate: " + elapsedTime);
//        }

        TaxiCabNumber.calculateV2(1729);

    }
}
