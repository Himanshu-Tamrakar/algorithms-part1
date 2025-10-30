package PerfectBalanceRedBlackTree.TwoDTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class TwoDTreePoint implements Comparable<TwoDTreePoint> {
    public static final Comparator<TwoDTreePoint> X_ORDER = new XOrder();
    public static final Comparator<TwoDTreePoint> Y_ORDER = new YOrder();

    private final double x;
    private final double y;

    public TwoDTreePoint(double x, double y) {

        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
        else          this.x = x;

        if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
        else          this.y = y;
    }

    public double x() { return this.x; }

    public double y() { return this.y; }

    public int compareTo(TwoDTreePoint that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<TwoDTreePoint> {
        public int compare(TwoDTreePoint p, TwoDTreePoint q) {
            return Double.compare(p.x, q.x);
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<TwoDTreePoint> {
        public int compare(TwoDTreePoint p, TwoDTreePoint q) {
            return Double.compare(p.y, q.y);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        TwoDTreePoint that = (TwoDTreePoint) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(TwoDTreePoint that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
}
