package MergeSort;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Merge {

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert  isSorted(a, mid+1, hi);

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }

    private static void merge(Object[] a, Object[] aux, Comparator comparator, int lo, int mid, int hi) {
        assert isSorted(a, comparator, lo, mid);
        assert isSorted(a, comparator, mid+1, hi );
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (less(comparator, aux[j], aux[i]))  a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }
        assert isSorted(a, comparator, lo, hi);

    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
        assert isSorted(a);
    }

    public static void sort(Object[] a, Comparator comparator) {
        Object[] aux = new Object[a.length];
        sort(a, aux, comparator, 0, a.length-1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void sort(Object[] a, Object[] aux, Comparator comparator, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, comparator, lo, mid);
        sort(a, aux, comparator, mid+1, hi);
        merge(a, aux, comparator, lo, mid, hi);
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int v, int w) {
        Object temp = a[v];
        a[v] = a[w];
        a[w] = temp;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
    private static void show(Object[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }


    // TEST with tiny.txt and words2.txt
    public static void main(String[] args) {
        String[] a = readFileAsWords("/home/decimal/personal/algorithms/temp/src/MergeSort/words3.txt");
        sort(a);
        show(a);

        exmapleComparator();
    }

    public static String[] readFileAsWords(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(" "); // keep adding lines with space
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Split the whole content by spaces
        return sb.toString().trim().split("\\s+");
    }

    public static void exmapleComparator() {
        class Student {
            public static Comparator ORDER_BY_NAME = new ByName();
            public static Comparator ORDER_BY_SECTION = new BySection();
            String name;
            int section;
            Student(String n, Integer sec) {
                name = n;
                section = sec;
            }

            @Override
            public String toString() {
                return name + " : " + section;
            }

            private static class ByName implements Comparator<Student> {
                @Override
                public int compare(Student student, Student t1) {
                    return student.name.compareTo(t1.name);
                }
            }

            private static class BySection implements Comparator<Student> {

                @Override
                public int compare(Student student, Student t1) {
                    return student.section - t1.section;
                }
            }
        }

        Student himanshu = new Student("Himanshu", 1);
        Student aman = new Student("Aman", 2);
        Student[] students = new Student[] {himanshu, aman};

        sort(students, Student.ORDER_BY_SECTION);
        show(students);
    }

}
