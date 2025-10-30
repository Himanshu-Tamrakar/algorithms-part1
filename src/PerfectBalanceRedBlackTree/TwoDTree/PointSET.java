package PerfectBalanceRedBlackTree.TwoDTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;


public class PointSET {
    private final TreeSet<Point2D> points;
    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
     return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        points.add(p);
    }

    public boolean contains(Point2D p)  {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p: points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> list = new LinkedList<>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Point2D nearestPoint = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point: points) {
            if (point.distanceSquaredTo(p) < distance) {
                distance = point.distanceSquaredTo(p);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
