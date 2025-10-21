package PerfectBalanceRedBlackTree.LineSegmentIntersectin;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Line implements Comparable<Line> {

    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return this.x1;
    }
    public int getY1() {
        return this.y1;
    }
    public int getX2() {
        return  this.x2;
    }
    public int getY2() {
        return this.y2;
    }

    public void drawPoint() {
        StdDraw.point(x1, y1);
        StdDraw.point(x2, y2);
    }

    public void draw() {
        StdDraw.line(x1, y1, x2, y2);
    }

    public String toString() {
        return "(" + x1 + ", " + y1 + ")" + " -> " + "(" + x2 + ", " + y2 + ")";
    }

    public boolean isHorizontalLine() {
        return y1 == y2;
    }

    @Override
    public int compareTo(Line that) {
        if (this.x1 < that.x1) return -1;
        else if (this.x1 > that.x1) return 1;
        else return Integer.compare(this.y1, that.y1);
    }

    public static class CompareByX implements Comparator<Line> {

        @Override
        public int compare(Line line, Line that) {
            if (line.x1 != that.x1) return Integer.compare(line.x1, that.x1);
            return Integer.compare(line.y1, that.y1);
        }
    }

    public static class CompareByY implements Comparator<Line> {

        @Override
        public int compare(Line line, Line that) {
            if (line.y1 != that.y1) return Integer.compare(line.y1, that.y1);
            return Integer.compare(line.x1, that.x1);
        }
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

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setXscale(0, 2048);
        StdDraw.setYscale(0, 2048);
        for (Line l : lines) {
            l.draw();
        }
        StdDraw.show();

        Arrays.sort(lines, new Line.CompareByY());

        for (Line l : lines) {
            System.out.println(l);
        }
    }

}

