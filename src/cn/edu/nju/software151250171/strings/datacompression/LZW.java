package cn.edu.nju.software151250171.strings.datacompression;

import edu.princeton.cs.algs4.TST;

/**
 * LZW压缩算法
 * 为输入中的变长模式生成一张定长的编码编译表
 * 和霍夫曼编码不同，输出中不需要附上这张编译表
 */
public class LZW {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    // Do not instantiate.
    private LZW() {
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     * 从81开始不断为新键赋予更大的编码值
     * 找出未处理的输入在符号表中最长的前缀字符串s
     * 输出s的8位值
     * 继续扫描s之后的一个字符c
     * 在符号表中将s+c(连接s和c)的值设为下一个编码值
     */
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R + 1;  // R is codeword for EOF，R为文件结束（EOF）的编码

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.找到匹配的最长前缀
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.打印出s的编码
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.将s加入符号表
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);            // Scan past s in input.从输入中读取s
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bit encoded using LZW compression with
     * 12-bit codewords from standard input; expands them; and writes
     * the results to standard output.
     * 输出当前字符串val
     * 在输入中读取一个编码x
     * 在符号表中将s设为和x相关联的值
     * 在符号表中将下一个未分配的编码值设为val+c,其中c为s的首字母
     * 将当前字符串val设为s
     */
    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        //用字符初始化编译表
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);//输出当前字符串
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];//读取下一个编码
            if (i == codeword) s = val + val.charAt(0);   // special case hack 如果前瞻字符不可用，根据上一个字符串的首字母得到编码的字符串
            if (i < L) st[i++] = val + s.charAt(0);//为编译表添加新的条目
            val = s;//更新当前编码
        }
        BinaryStdOut.close();
    }
}
