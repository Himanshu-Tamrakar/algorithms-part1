package DataCompression;

import edu.princeton.cs.algs4.*;

import java.awt.*;

public class InterviewDataCompression {


    public InterviewDataCompression() {
        BinaryDump.main(new String[] {});
    }

    public static void binaryDump() {
        int bitsPerLine = 8;
        int count;
        for (count = 0; !BinaryStdIn.isEmpty(); count++) {
            if (bitsPerLine == 0) {
                BinaryStdIn.readBoolean();
                continue;
            }
            else if (count != 0 && count % bitsPerLine == 0) StdOut.println();
            if (BinaryStdIn.readBoolean()) StdOut.print(1);
            else                           StdOut.print(0);
        }
        if (bitsPerLine != 0) StdOut.println();
        StdOut.println(count + " bits");
    }

    public static void hexDump() {
        int bytesPerLine = 16;

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) StdOut.printf("");
            else if (i % bytesPerLine == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (bytesPerLine != 0) StdOut.println();
        StdOut.println((i*8) + " bits");
    }

    public static void pictureDump() {
        int width = 2000;
        int height = 500;
        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!BinaryStdIn.isEmpty()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) picture.set(col, row, Color.BLACK);
                    else     picture.set(col, row, Color.WHITE);
                }
                else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }

    public static void main(String[] args) {
//        InterviewDataCompression.binaryDump();
//        InterviewDataCompression.hexDump();
        InterviewDataCompression.pictureDump();
    }
}
