/**
 * Graham Scan;
 * Algorithms breakdown
 * How to find point p with the smallest of y coordinate?
 * Sort Point@d by Y_ORDER
 *
 * How to sort points by polar angle with respect to point p which is p[0]. We have already sorted points by Y_ORDER
 * SORT BY Polar Angle with respect to p[0]
 *
 * How to determine p1 -> p2 -> p3 is counterclockwise.
 * User ccw method if < 0 means clockwise, == 0 means collinear, >0 mean counterclockwise
 *
 * Sort efficiently.
 * User Array.sort method; Merge or Quick.
 *
 * How to handle degeneracies(thee or more points on line)
 * First find second extram push And find first fird point which is not colinear. does not matter if thirs is click or counterclock
 * in ccw check for <= case
 */
package ElementrySort;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class ConvexHull {
    Stack<Point2D> hull = new Stack<>();
    public ConvexHull(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException("argument is null");
        if (points.length == 0) throw new IllegalArgumentException("array is of length 0");

        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points[" + i + "] is null");
            a[i] = points[i];
        }

        // Sort point via Y coordinate
        Arrays.sort(a, Point2D.Y_ORDER);

        // sort by polar angle with respect to base point a[0]
        Arrays.sort(a, a[0].polarOrder());

        // a[0] is exrteam point as sorted other points with respect to a[0]
        hull.push(a[0]);       // a[0] is first extreme point

        // find index k1 of first point not equal to a[0]
        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!a[0].equals(a[k1])) break;
        if (k1 == n) return;        // all points equal

        // find index k2 of first point not collinear with a[0] and a[k1]
        int k2;
        for (k2 = k1+1; k2 < n; k2++)
            if (Point2D.ccw(a[0], a[k1], a[k2]) != 0) break;
        hull.push(a[k2-1]);    // a[k2-1] is second extreme point

        // Graham scan; note that a[n-1] is extreme point different from a[0]
        // ccw is checking three point p1, p2. p3 is counterclockwise or now
        for (int i = k2; i < n; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, a[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(a[i]);
        }
    }

    Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<Point2D>();
        for (Point2D p : hull) s.push(p);
        return s;
    }
}
