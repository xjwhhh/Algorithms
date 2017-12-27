package cn.edu.nju.software151250171.search.searchingapplications;

import java.io.File;
import java.util.Set;

import cn.edu.nju.software151250171.search.symboltables.SET;
import cn.edu.nju.software151250171.search.symboltables.ST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

/*
 * 从命令行接受多个文件名并使用一张符号表来构造一个反向索引，它能够将任意文件中的任意一个单词和一个
 * 出现过这个单词的所有文件的文件名构成的SET对象关联起来。在接受标准输入的查询时，，输出单词对应的文件列表
 */
public class FileIndex {
    public static void main(String args[]) {
        ST<String, SET<File>> st = new ST<String, SET<File>>();

        for (String filename : args) {
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) {
                    st.put(word, new SET<File>());
                }
                SET<File> set = st.get(word);
                set.add(file);
            }
        }

        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                for (File file : st.get(query)) {
                    System.out.println(" " + file.getName());
                }
            }
        }
    }
}
