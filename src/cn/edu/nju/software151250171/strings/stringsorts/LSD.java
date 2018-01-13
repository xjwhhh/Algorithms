package cn.edu.nju.software151250171.strings.stringsorts;

/**
 * 低位优先的字符串排序
 * 是一种适用于一般应用的线性时间排序算法
 * 要将每个元素均为含有W个字符的字符串数组a[]排序，要进行W次键索引计数排序：从右到左，以每个位置的的字符为键排序一次
 */
public class LSD {
    //通过前W个字符将a[]排序
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        //根据第d个字符用键索引计数法排序
        for (int d = W - 1; d >= 0; d--) {
            //计算出现频率
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }
            //将频率转换为索引
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }
            //将元素分类
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            //回写
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }
}
