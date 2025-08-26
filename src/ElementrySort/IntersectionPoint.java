/*
  Question 1
  Intersection of two sets.
  Given two arrays a[] and b[], each containing N distinct 2D points in the plane,
  design a subquadratic algorithm to count the number of points that are contained both in array a[] and array b[].
 */
package ElementrySort;

import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.StdOut;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IntersectionPoint {
    private static class Point implements Comparable<Point> {
        private int x;
        private int y;
        public Point(int xAxis, int yAxix) {
            x = xAxis;
            y = yAxix;
        }

        @Override
        public int compareTo(Point that) {
            if (this.y < that.y) return -1;
            if (this.y > that.y) return +1;
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
            return 0;
        }
    }

    // No Duplicate points present
    public static int countIntersections(Point[] a, Point[] b) {
        Shell.sort(a);
        Shell.sort(b);

        int i = 0;
        int j = 0;
        int count = 0;

        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) == 0) {
                count++;
                i++;
                j++;
            }
            else if (a[i].compareTo(b[j]) < 0) i++;
            else j++;
        }
        return count;
    }



    public static void main(String[] args) throws FileNotFoundException {
        try {
            File file = new File("/home/decimal/personal/algorithms/temp/src/ElementrySort/IntersectionPointTestData.txt");
            Scanner  scanner = new Scanner(file);

            int n = scanner.nextInt();
            Point[] a = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                a[i] = new Point(x, y);
            }
            int m = scanner.nextInt();
            Point[] b = new Point[m];
            for (int i = 0; i < m; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                b[i] = new Point(x, y);
            }
            StdOut.println("Total intersection point alogn(Duplicate Points are allowed): " +  countIntersections(a, b));

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }

    }

}
