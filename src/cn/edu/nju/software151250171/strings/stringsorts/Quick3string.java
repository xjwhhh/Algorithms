package cn.edu.nju.software151250171.strings.stringsorts;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 三向字符串快速排序
 * 能够很好处理等值键，有较长公共前缀的键，取值范围较小的键和小数组
 * 在将字符串数组a[]排序时，根据他们的首字母进行三向切分，然后递归的将得到的三个子数组排序
 * 一个含有所有首字母小于切分字符的字符串子数组，一个含有所有首字母等于切分字符的字符串子数组（排序时忽略他们的首字母），一个含有所有首字母大于切分字符的字符串子数组
 */
public class Quick3string {
    private static final int CUTOFF =  15;   // cutoff to insertion sort

    // do not instantiate
    private Quick3string() { }

    /**
     * Rearranges the array of strings in ascending order.
     *
     * @param a the array to be sorted
     */
    public static void sort(String[] a) {
        StdRandom.shuffle(a);
        //在排序之前将数组打乱或是将第一个元素与一个随机位置的元素交换得到一个随机的切分元素
        //预防数组已经有序或是接近有序的最坏情况
        sort(a, 0, a.length-1, 0);
        assert isSorted(a);
    }

    // return the dth character of s, -1 if d = length of s
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }


    // 3-way string quicksort a[lo..hi] starting at dth character
    private static void sort(String[] a, int lo, int hi, int d) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);//切分字符
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if      (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else              i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    // DEPRECATED BECAUSE OF SLOW SUBSTRING EXTRACTION IN JAVA 7
    // private static boolean less(String v, String w, int d) {
    //    assert v.substring(0, d).equals(w.substring(0, d));
    //    return v.substring(d).compareTo(w.substring(d)) < 0;
    // }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    // is the array sorted
    private static boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i].compareTo(a[i-1]) < 0) return false;
        return true;
    }
}
