package cn.edu.nju.software151250171.sort;

import edu.princeton.cs.algs4.StdOut;

/**
 * 生成使得普通快速排序表现最佳的数组（无重复元素）：
 * 数组大小为N且不包含重复元素，每次切分后两个子数组的大小最多差1
 * （子数组的大小与含有N个相同元素的数组的切分情况相同）
 */
public class QuickBest {

    // postcondition: a[lo..hi] is best-case input for quicksorting that subarray
    private static void best(int[] a, int lo, int hi) {

        // precondition:  a[lo..hi] contains keys lo to hi, in order
        for (int i = lo; i <= hi; i++)
            assert a[i] == i;

        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        best(a, lo, mid-1);
        best(a, mid+1, hi);
        exch(a, lo, mid);
    }

    public static int[] best(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i;
        best(a, 0, n-1);
        return a;
    }

    // exchange a[i] and a[j]
    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static void main(String[] args) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int n = Integer.parseInt(args[0]);
        int[] a = best(n);
        for (int i = 0; i < n; i++)
            // StdOut.println(a[i]);
            StdOut.print(alphabet.charAt(a[i]));
        StdOut.println();
    }
}
