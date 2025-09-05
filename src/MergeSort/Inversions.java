package MergeSort;

public class Inversions {
    private int _inversions = 0;
    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
                _inversions += mid - i + 1;
            }
            else                            a[k] = aux[i++];
        }

    }

    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public int inversions() {
        return _inversions;
    }

    public void sort(Comparable[] a, Comparable[] aux,  int lo, int hi) {
        if (hi <= lo) return;;
        int mid = lo + ( hi - lo ) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
}
