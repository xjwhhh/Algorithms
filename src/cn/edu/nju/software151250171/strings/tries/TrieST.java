package cn.edu.nju.software151250171.strings.tries;

import cn.edu.nju.software151250171.base.Queue;

public class TrieST<Value> {
    private static int R = 256;//基数
    private Node root;//单词查找树的根结点

    private int n;          // number of keys in trie

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    //返回以x为根结点的子单词查找树中与key相关联的值
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);//找到第d个字符所对应的的子单词查找树
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    //如果key存在于以x为根结点的子单词查找树中则更新与它相关联的值
    //不存在就创建新的结点并将键的值保存到最后一个字符的结点中
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);//找到第d个字符所对应的的子单词查找树
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;

    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    //在访问一个结点时，如果它的值非空，将与它相关联的字符串加入到队列中，然后递归的访问它的链接数组所指向的所有可能的字符结点
    //在每次调用前，都将链接对应的字符附加到当前键的末尾作为调用的参数键
    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) {
            return;
        }
        if (x.val != null) {
            q.enqueue(pre);
        }
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }

    //通配符匹配
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    //添加一个参数pattern来制定匹配的模式
    //如果模式中含有通配符，就需要递归调用处理所有的链接，否则就只需要处理模式中指定的字符的链接即可
    //不需要考虑长度超过模式字符串的键
    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    //找到给定字符串的最长键前缀
    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of given length (-1 if no such match)
    //记录查找路径上所找到的最长键的长度(将它作为递归方法的参数并在遇到值非空的结点时更新它)
    //查找会在被查找的字符串结束或是遇到空链接时停止
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d + 1, length);
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    //找到键所对应的结点并将它的值设为空
    //如果该节点含有一个非空的链接指向某个子节点，那就不需要再进行其他操作
    //如果它的所有链接均为空，那就需要从数据结构中删除这个节点
    //如果删去这个节点导致它的父结点的所有连接也均为空，就需要继续删除它的父结点，依此类推
    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) {
                n--;
            }
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

}
