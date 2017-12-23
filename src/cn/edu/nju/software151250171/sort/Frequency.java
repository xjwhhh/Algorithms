package cn.edu.nju.software151250171.sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * 从标准输入读取一列字符串并按照字符串出现频率由高到低的顺序打印出每个字符串及其出现次数
 */
public class Frequency {

    public static void main(String[] args) {

        // read in and sort the input strings
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        Arrays.sort(a);
//
//        // create an array of distinct strings and their frequencies
//        Record[] records = new Record[n];
//        String word = a[0];
//        int freq = 1;
//        int m = 0;
//        for (int i = 1; i < n; i++) {
//            if (!a[i].equals(word)) {
//                records[m++] = new Record(word, freq);
//                word = a[i];
//                freq = 0;
//            }
//            freq++;
//        }
//        records[m++] = new Record(word, freq);
//
//        // sort by frequency and print results
//        Arrays.sort(records, 0, m);
//        for (int i = m-1; i >= 0; i--)
//            StdOut.println(records[i]);
    }
}