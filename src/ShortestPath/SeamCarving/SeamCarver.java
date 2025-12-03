package ShortestPath.SeamCarving;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private EdgeWeightedDigraph G;
    private Picture picture;
    private int[][] edgeTo;
    private  double[] distTo;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= picture.width()) throw new IllegalArgumentException();
        if (y < 0 || y >= picture.height()) throw new IllegalArgumentException();
        if (x == 0 || y == 0 || x == picture.width()-1 || y == picture.height()-1) return 1000;

        Color colorLeft = picture.get(x-1, y);
        Color colorRight = picture.get(x+1, y);
        int colR = colorRight.getRed() - colorLeft.getRed();
        int colG = colorRight.getGreen() - colorLeft.getGreen();
        int colB = colorRight.getBlue() - colorLeft.getBlue();
        int energyX = colR * colR + colG * colG + colB * colB;

        Color colorBottom = picture.get(x, y-1);
        Color colorUp = picture.get(x, y+1);
        int rowR = colorUp.getRed() - colorBottom.getRed();
        int rowG = colorUp.getGreen() - colorBottom.getGreen();
        int rowB = colorUp.getBlue() - colorBottom.getBlue();
        int energyY = rowR * rowR + rowG * rowG + rowB * rowB;

        return Math.sqrt(energyX + energyY);
    }

    public int[] findHorizontalSeam() {
        int[] horizontalSeam = new int[picture.width()];
        distTo = new double[picture.height()];
        edgeTo = new int[picture.height()][picture.width()];
        for (int y = 0; y < picture.height(); y++) {
            distTo[y] = 1000.00;
        }

        double[][] energyAll = new double[picture.height()][picture.width()];
        for (int x = 0; x < picture.width(); x++) { // x is column
            for (int y = 0; y < picture.height(); y++) { // y is row
                energyAll[y][x] = energy(x, y);
            }
        }

        for (int x = 1; x < picture.width(); x++) {
            double[] prevDistTo = distTo.clone();

            // Current row dist is INFINITY to find.
            for (int y = 0; y < picture.height(); y++) {
                distTo[y] = Double.POSITIVE_INFINITY;
            }

            for (int y = 0; y < picture.height(); y++) {
                relaxH(x, y, prevDistTo, y-1, energyAll[y][x]);
                relaxH(x, y, prevDistTo, y, energyAll[y][x]);
                relaxH(x, y, prevDistTo, y+1, energyAll[y][x]);
            }

        }

        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        // After above loop distTo will be last row.
        for (int x = 0; x < distTo.length; x++) {
            if (min > distTo[x]) {
                min = distTo[x];
                minIndex = x;
            }
        }

        for (int x = picture.width()-1; x >= 0 ; x--) {
            horizontalSeam[x] = minIndex;
            minIndex = edgeTo[minIndex][x];
        }
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] verticalSeam = new int[picture.height()];

        distTo = new double[picture.width()];
        edgeTo = new int[picture.height()][picture.width()];

        // top row of picture is 1000
        for (int x = 0; x < picture.width(); x++) {
            distTo[x] = 1000.00;
        }


        double[][] energyAll = new double[picture.height()][picture.width()]; // width is columns, height is row
        for (int x = 0; x < picture.width(); x++) { // x is column
            for (int y = 0; y < picture.height(); y++) { // y is row
                energyAll[y][x] = energy(x, y);
            }
        }

        for (int y = 1; y < picture.height(); y++) { // row or height
            double[] prevDistTo = distTo.clone();

            // Current row dist is INFINITY to find.
            for (int x = 0; x < picture.width(); x++) {
                distTo[x] = Double.POSITIVE_INFINITY;
            }
            for (int x = 0; x < picture.width(); x++) { // columns or width

                relaxV(x, y, prevDistTo, x-1, energyAll[y][x]); // prevDistTo means previous row(height) and x-1 means previous column
                relaxV(x, y, prevDistTo, x, energyAll[y][x]); // prevDistTo means previous row(height) and x means same column
                relaxV(x, y, prevDistTo, x+1, energyAll[y][x]); // prevDistTo means previous row(height) and x+1 means next column

            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        // After above loop distTo will be last row.
        for (int x = 0; x < distTo.length; x++) {
            if (min > distTo[x]) {
                min = distTo[x];
                minIndex = x;
            }
        }

        for (int y = picture.height()-1; y >= 0 ; y--) {
            verticalSeam[y] = minIndex;
            minIndex = edgeTo[y][minIndex];
        }
        return verticalSeam;
    }

    private void relaxV(int x, int y, double[] prevDistTo, int prev, double energy) {
        if (prev < 0 || prev > picture.width() - 1) {
            return;
        }

        if (distTo[x] > prevDistTo[prev] + energy) {
            distTo[x] = prevDistTo[prev] + energy;
            edgeTo[y][x] = prev;
        }
    }

    private void relaxH(int x, int y, double[] prevDistTo, int prev, double energy) {
        if (prev < 0 || prev > picture.height() - 1) {
            return;
        }

        if (distTo[y] > prevDistTo[prev] + energy) {
            distTo[y] = prevDistTo[prev] + energy;
            edgeTo[y][x] = prev;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length > picture.width()) throw new IllegalArgumentException();
        if (picture.height() <= 1) throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > picture.height() - 1) {
                throw new IllegalArgumentException("out of range");
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("difference is big");
            }
        }

        Picture carvedPicture = new Picture(picture.width(), picture.height()-1);
        for (int x = 0; x < picture.width(); x++) {
            int skipHorizontalSeam = seam[x];
            for (int y = 0; y < picture.height()-1; y++) {
                if (y != skipHorizontalSeam) {
                    carvedPicture.set(x, y, picture.get(x, y));
                }
            }
        }

        picture = carvedPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length > picture.height()) throw new IllegalArgumentException();
        if (picture.width() <= 1) throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > picture.width() - 1) {
                throw new IllegalArgumentException("out of range");
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("difference is big");
            }
        }

        Picture carvedPicture = new Picture(picture.width()-1, picture.height());
        for (int y = 0; y < picture.height(); y++) {
            int skipVerticalSeam = seam[y];
            for (int x = 0; x < picture.width()-1; x++) {
                if (x != skipVerticalSeam) {
                    carvedPicture.set(x, y, picture.get(x, y));
                }
            }
        }
        picture = carvedPicture;

    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture1 = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/6x5.png");

        SeamCarver seamCarver = new SeamCarver(picture1);
        int[] seamV = seamCarver.findVerticalSeam();
        for (int i: seamV) {
            System.out.println(i);
        }

        System.out.println("-----");

        int[] seamH = seamCarver.findHorizontalSeam();
        for (int i: seamH) {
            System.out.println(i);
        }


    }

}
