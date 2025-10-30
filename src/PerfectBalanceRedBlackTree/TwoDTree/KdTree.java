package PerfectBalanceRedBlackTree.TwoDTree;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private class Node {
        Point2D point;
        int level;
        int size = 0;
        Node left, right;

        public Node(Point2D p, int lev, int sz) {
            point = p;
            level = lev;
            size = sz;
        }
    }

    private Node root;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;

        if (p.equals(x.point)) return true;

        boolean isVertical = x.level % 2 == 0;
        int cmp;
        if (isVertical) {
            cmp = Point2D.X_ORDER.compare(p, x.point);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.point);
        }

        if (cmp <= 0) return contains(x.left, p);
        else return contains(x.right, p);

    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, root);
    }
    private Node insert(Node x, Point2D p, Node parent) {
        if (x == null && parent == null) return new Node(p, 0, 1);
        if (x == null) return new Node(p, parent.level + 1, 1);

        if (p.equals(x.point)) return x;

        boolean isVertical = x.level % 2 == 0;
        int cmp;
        if (isVertical) {
            cmp = Point2D.X_ORDER.compare(p, x.point);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.point);
        }

        if (cmp < 0) {
            x.left = insert(x.left, p, x);
        } else if (cmp > 0) {
            x.right = insert(x.right, p, x);
        } else {
            x.left = insert(x.left, p, x);
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void draw() {
        draw(root, new RectHV(0, 0, 1, 1));
    }

    private void draw(Node x, RectHV rect) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        x.point.draw();
        boolean isVertical = x.level % 2 == 0;
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), rect.ymin(), x.point.x(), rect.ymax());
            draw(x.left, new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax()));
            draw(x.right, new RectHV(x.point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), x.point.y(), rect.xmax(), x.point.y());
            draw(x.left, new RectHV(rect.xmin(), rect.ymin(), rect.ymax(), x.point.y()));
            draw(x.right, new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax()));
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new LinkedList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node x, RectHV rect, List<Point2D> list) {
        if (x == null) return;
        if (rect.contains(x.point)) list.add(x.point);
        boolean isVertical = x.level % 2 == 0;
        if (isVertical) {
            if (rect.xmin() < x.point.x() || rect.xmin() == x.point.x()) {
                range(x.left, rect, list);
            }
            if (rect.xmax() > x.point.x() || rect.xmax() == x.point.x()) {
                range(x.right, rect, list);
            }
        } else {
            if (rect.ymin() < x.point.y() || rect.ymax() == x.point.y()) {
                range(x.left, rect, list);
            }
            if (rect.ymax() > x.point.y()|| rect.ymax() == x.point.y()) {
                range(x.right, rect, list);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root.point, root);
    }

    private Point2D nearest(Point2D p, Point2D currentNearestPoint, Node n) {

        if (n == null) {
            return currentNearestPoint;
        }
        if (n.level % 2 == 0) {
            if (p.x() > n.point.x()) { // check right sub-tree
                Point2D npr = nearest(p, n.point.distanceSquaredTo(p) < currentNearestPoint.distanceSquaredTo(p) ? n.point : currentNearestPoint, n.right);
                if (npr.distanceSquaredTo(p) > Math.abs(n.point.x() - p.x())) {
                    Point2D npl = nearest(p, npr, n.left);
                    return npr.distanceSquaredTo(p) > npl.distanceSquaredTo(p) ? npl : npr;
                } else {
                    return npr;
                }
            } else { // check left sub-tree
                Point2D npl = nearest(p, n.point.distanceSquaredTo(p) < currentNearestPoint.distanceSquaredTo(p) ? n.point : currentNearestPoint, n.left);
                if (npl.distanceSquaredTo(p) > Math.abs(n.point.x() - p.x())) {
                    Point2D npr = nearest(p, npl, n.right);
                    return npr.distanceSquaredTo(p) > npl.distanceSquaredTo(p) ? npl : npr;
                } else {
                    return npl;
                }
            }
        } else {
            if (p.y() > n.point.y()) { // check up sub-tree
                Point2D npu = nearest(p, n.point.distanceSquaredTo(p) < currentNearestPoint.distanceSquaredTo(p) ? n.point : currentNearestPoint, n.right);
                if (npu.distanceSquaredTo(p) > Math.abs(n.point.y() - p.y())) {
                    Point2D npd = nearest(p, npu, n.left);
                    return npu.distanceSquaredTo(p) > npd.distanceSquaredTo(p) ? npd : npu;
                } else {
                    return npu;
                }
            } else { // check down sub-tree
                Point2D npd = nearest(p, n.point.distanceSquaredTo(p) < currentNearestPoint.distanceSquaredTo(p) ? n.point : currentNearestPoint, n.left);
                if (npd.distanceSquaredTo(p) > Math.abs(n.point.y() - p.y())) {
                    Point2D npu = nearest(p, npd, n.right);
                    return npu.distanceSquaredTo(p) > npd.distanceSquaredTo(p) ? npd : npu;
                } else {
                    return npd;
                }
            }
        }
    }

    public static void main(String[] args) {

        In in = new In("/home/decimal/personal/algorithms/temp/src/PerfectBalanceRedBlackTree/TwoDTree/input.txt");
        int n = 10;
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            String[] line = in.readLine().trim().split("\\s+");
            double x = Double.parseDouble(line[0]);
            double y = Double.parseDouble(line[1]);
            points[i] = new Point2D(x, y);
        }

        KdTree kdTree = new KdTree();

        for (int i = 0; i < n; i++) {
            kdTree.insert(points[i]);
        }

        for (Point2D p: kdTree.range(new RectHV(0.25, 0.4375, 0.5625, 0.8125))) {
            StdOut.println(p);
        }



    }

}
