package MergeSort.CollinearPoint;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * sorting rule (or comparator logic) for two 2D points
     * look at the y-coordinate of both points.
     * If the y-coordinates are equal (tie), then compare the x-coordinates.
     * @param that
     * @return
     */
    @Override
    public int compareTo(Point that) {
        if      (this.y > that.y) return 1;
        else if (this.y < that.y) return -1;
        else return Integer.compare(this.x, this.y);
    }

    /**
     * A positive slope means the line goes upward as you move left → right.
     * A negative slope means the line goes downward as you move left → right.
     * A zero slope means the line is flat (horizontal).
     * An undefined slope occurs when 𝑥2 = 𝑥1
     * x2=x1 (vertical line).
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Pick one reference point (say 𝑃 = (𝑥0 , 𝑦0) P=(x0 ,y0)).
     * For any two other points 𝑄 = (𝑥1, 𝑦1) Q=(x1, y1) and 𝑅 = (𝑥2 ,𝑦2) R=(x2,y2):
     * Compute the slope of the line from 𝑃 to 𝑄.
     * Compute the slope of the line from 𝑃 to R.
     * Compare those two slope values.
     *
     * @return
     * If slope(PQ) < slope(PR), then Q comes before R.
     * If slope(PQ) > slope(PR), then R comes before Q.
     * If they are equal, the points are collinear with P.
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrdering();
    }

    private class SlopeOrdering implements Comparator<Point> {

        @Override
        public int compare(Point q1, Point q2) {
            double slope1 = slopeTo(q1);
            double slope2 = slopeTo(q2);
            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        In in = new In("/home/decimal/personal/algorithms/temp/src/MergeSort/CollinearPoints/input8.txt");

        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setXscale(0, 2048);
        StdDraw.setYscale(0, 2048);
        StdDraw.setPenRadius(.02);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    }


}
