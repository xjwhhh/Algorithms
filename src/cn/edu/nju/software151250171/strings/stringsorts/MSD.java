package cn.edu.nju.software151250171.strings.stringsorts;

/**
 * 高位优先的字符串排序
 * 在将一个字符串数组a[]排序时，首先根据他们的首字母用键索引计数法进行排序，然后递归地根据子数组中的字符串的首字母将子数组进行排序
 */
public class MSD {
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;   // each Java int is 32 bits
    private static final int R = 256;   // extended ASCII alphabet size,基数
    private static final int CUTOFF = 15;   // cutoff to insertion sort,小数组的切换阈值

    private static String[] aux;                    //数据分类的辅助数组

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void sort(String[] a, int lo, int hi, int d) {

        // cutoff to insertion sort for small subarrays
        //在子数组较小时切换插入排序
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }

    // insertion sort a[lo..hi], starting at dth character
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    //对前d个字符均相同的字符串执行插入排序，代码的效率取决于substring()方法所需的时间是否是常数
    public static void insertSort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > lo && insertLess(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static boolean insertLess(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

}
