package cn.edu.nju.software151250171.strings.substringsearch;

public class Brute {
    // return offset of first match or n if no match
    //指针i跟踪文本，指针j跟踪模式
    //对于每个i，代码首先将j重置为0并不断将它增大，直到找到了一个不匹配的字符或是模式结束位置
    //如果在模式字符串结束之前文本字符串就已经结束了，那么就没有找到匹配：模式字符串在文本中不存在
    //i指向的是文本中已经匹配过的字符序列的开头
    public static int search1(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == m) return i;            // found at offset i
        }
        return n;                            // not found
    }

    // return offset of first match or N if no match
    //search2中的i值相当于search1中的i+j，指向的是文本中已经匹配过的字符序列的末端
    //如果i和j指向的字符不匹配了，那么需要回退这两个指针的值：将j重新指向模式的开头，将i指向本次匹配的开始位置的下一个字符
    public static int search2(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) return i - m;    // found
        else        return n;        // not found
    }
}
