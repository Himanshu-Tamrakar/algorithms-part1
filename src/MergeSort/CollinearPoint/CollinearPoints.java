package MergeSort.CollinearPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    public CollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("null argument to constructor");
        checkNullEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException("One of the point in points array is null");
            }
        }
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
    }
}
