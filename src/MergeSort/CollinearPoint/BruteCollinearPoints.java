package MergeSort.CollinearPoint;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSegments = new LinkedList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("null argument to constructor");
        checkNullEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);

        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i+1; j < pointsCopy.length; j++) {
                for (int k = j+1; k < pointsCopy.length; k++) {
                    for (int l = k+1; l < pointsCopy.length; l++) {
                        Point p = pointsCopy[i];
                        Point q = pointsCopy[j];
                        Point r = pointsCopy[k];
                        Point s = pointsCopy[l];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            LineSegment tempLineSegment = new LineSegment(p, s);
                            if (!lineSegments.contains(tempLineSegment)) {
                                lineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                            }
                        }
                    }
                }
            }

        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
//        LineSegment[] array = (LineSegment[]) lineSegments.toArray();
//        return array;

        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException("One of the point in points array is null");
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("/home/decimal/personal/algorithms/temp/src/MergeSort/CollinearPoint/input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setXscale(0, 16384);
        StdDraw.setYscale(0, 16384);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            segment.draw();
        }
        StdDraw.show();
    }
}
