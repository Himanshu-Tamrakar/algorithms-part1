package PerfectBalanceRedBlackTree.LineSegmentIntersectin;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineSegmentIntersection {
    private final List<Line[]> intersections;
    public LineSegmentIntersection(Line[] lines) {
        intersections = new ArrayList<>();

        // Balanced BST: Active horizontal segments keyed by their Y-coordinate
        RedBlackBST<Integer, Line> st = new RedBlackBST<>();

        // Priority queue of all events (sorted by x-coordinate)
        MinPQ<Event> pq = new MinPQ<>();

        for (Line line : lines) {
            Event leftEvent = new Event(line.getX1(), line.getY1(), line, Event.Type.LEFT);
            Event rightEvent = new Event(line.getX2(), line.getY2(), line, Event.Type.RIGHT);
            pq.insert(leftEvent);
            pq.insert(rightEvent);
        }

        while (!pq.isEmpty()) {
            Event event = pq.delMin();
            Line segment = event.getSegment();

            if (event.getType() == Event.Type.LEFT) {
                if (segment.isHorizontalLine()) {
                    st.put(segment.getY1(), segment);
                } else {
                    // Vertical line: check for intersections with all active horizontal lines
                    int minY = Math.min(segment.getY1(), segment.getY2());
                    int maxY = Math.max(segment.getY1(), segment.getY2());

                    // Find all horizontal lines within this Y-range
                    Iterable<Integer> keysInRange = st.keys(minY, maxY);
                    for (int yKey : keysInRange) {
                        Line horizontal = st.get(yKey);
                        intersections.add(new Line[] { segment, horizontal });
                        System.out.printf("Intersection found: %s intersects %s%n", segment, horizontal);
                    }
                }
            } else {
                if (segment.isHorizontalLine()) {
                    st.delete(segment.getY1());
                }
            }
        }
        System.out.println("End");
    }

    public Iterable<Line[]> getIntersectins() {
        return intersections;
    }

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/PerfectBalanceRedBlackTree/LineSegmentIntersectin/lineinput1.txt");
        int n = in.readInt();
        Line[] lines = new Line[n];
        for (int i = 0; i < n; i++) {
            int x1 = in.readInt();
            int y1 = in.readInt();
            int x2 = in.readInt();
            int y2 = in.readInt();
            lines[i] = new Line(x1, y1, x2, y2);
        }

        LineSegmentIntersection lineSegmentIntersection = new LineSegmentIntersection(lines);
    }
}
