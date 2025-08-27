/*
   Question 3
   Dutch national flag. Given an array of N buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
   swap(i,j): swap the pebble in bucket i with the pebble in bucket j.
   color(i): color of pebble in bucket i.
   The performance requirements are as follows:
   At most N calls to color().
   At most N calls to swap().
   Constant extra space.
*/
package ElementrySort;

import edu.princeton.cs.algs4.StdOut;

import java.awt.desktop.AboutEvent;

// Pebbles should be ordered Red < White < Blue
enum Pebble {
    Red,
    White,
    Blue
}
public class Bucket {
    Pebble[] pebbles;

    public Pebble color(int i) {
        isValid(i);
        return pebbles[i];
    }

    private void swap(int i, int j) {
        isValid(i);
        isValid(j);
        Pebble temp = pebbles[i];
        pebbles[i] = pebbles[j];
        pebbles[j] = temp;
    }

    private int compare(Pebble p) {
        Pebble white = Pebble.White;
        return p.ordinal() - white.ordinal();
    }

    public void sort() {
        int i = 0;
        int j = pebbles.length-1;
        int runner = 0;

        while (runner <= j) {
            Pebble color = color(runner);
            int cmp = compare(color);
            if (cmp > 0) {
                swap(runner, j--);
            } else if (cmp < 0) {
                swap(runner++, i++);
            } else {
                runner++;
            }
        }
    }

    private boolean isValid(int i) {
        if (i < 0 || i >= pebbles.length) {
            throw new IllegalArgumentException("Array index out of bound");
        }
        return true;
    }

    public static void main(String[] args) {
        Pebble[] pebbles = new Pebble[4];
//        pebbles[0] = Pebble.Blue;
//        pebbles[1] = Pebble.White;
//        pebbles[2] = Pebble.Red;
//        pebbles[3] = Pebble.Red;

//        pebbles[0] = Pebble.Blue;
//        pebbles[1] = Pebble.White;
//        pebbles[2] = Pebble.Blue;
//        pebbles[3] = Pebble.Blue;

//        pebbles[0] = Pebble.Red;
//        pebbles[1] = Pebble.White;
//        pebbles[2] = Pebble.Red;
//        pebbles[3] = Pebble.White;

        pebbles[0] = Pebble.Red;
        pebbles[1] = Pebble.White;
        pebbles[2] = Pebble.Blue;
        pebbles[3] = Pebble.White;


        Bucket bucket = new Bucket();
        bucket.pebbles = pebbles;
        bucket.sort();
        for (int i = 0; i < 4; i++) {
            StdOut.println(bucket.pebbles[i]);
        }
    }
}
