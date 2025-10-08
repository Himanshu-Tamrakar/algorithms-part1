package QuickSort;

import edu.princeton.cs.algs4.In;

public class SelectionInTwoSortedArray {

    public int select(int[] input1, int[] input2, int k) {
        if (input1.length > input2.length) {
            return select(input2, input1, k);
        }

        int x = input1.length;
        int y = input2.length;

        int lo = Math.max(k-y, 0);
        int hi = Math.min(k, x);

        while (lo <= hi) {
            int partitionX =  (lo + hi) / 2;
            int partitionY = k - partitionX;
            int maxLeftX  = partitionX == 0 ? Integer.MIN_VALUE : input1[partitionX-1];
            int minRightX = partitionX == x ? Integer.MAX_VALUE : input1[partitionX];

            int maxLeftY = partitionY == 0 ? Integer.MIN_VALUE : input2[partitionY-1];
            int minRightY = partitionY == y ? Integer.MAX_VALUE : input2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                return Math.max(maxLeftX, maxLeftY);
            } else if(maxLeftX > minRightY) {
                hi = partitionX - 1;
            } else {
                lo = partitionX + 1;
            }
        }

        throw new IllegalArgumentException();
    }
    public static void main(String[] args) {
        int[] a = {1, 3, 8, 9, 15};
        int[] b = {7, 11, 18, 19, 21, 25};

        SelectionInTwoSortedArray selectionInTwoSortedArray = new SelectionInTwoSortedArray();
        int result = selectionInTwoSortedArray.select(a, b, 2);
        System.out.println(result);
    }
}
