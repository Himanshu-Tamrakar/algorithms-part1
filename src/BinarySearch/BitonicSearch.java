package BinarySearch;

import edu.princeton.cs.algs4.BinarySearch;

public class BitonicSearch {
    // This method should be called when array is bitomic
    private int getBitonicPoint(int[] a, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi-lo) / 2;
            if (a[mid] > a[mid-1] && a[mid] > a[mid+1]) return mid;

            if (a[mid] > a[mid-1] && a[mid] < a[mid+1]) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    private int binarySearch(int[] a, int lo, int hi, int key) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] < key) lo = mid + 1;
            else if (a[mid] > key) hi = mid - 1;
            else return mid;
        }
        return -1;
    }

    private boolean isBitonicArray(int[] a) {
        int i = 1;
        int n = a.length;
        while (i < n && a[i] > a[i-1]) {
            i++;
        }

        // All increasing and all decreasing are not bitonc
        if (i == 1 && i == n) {
            return false;
        }

        while (i < n && a[i] < a[i-1]) {
            i++;
        }

        return i == n;
    }
}
