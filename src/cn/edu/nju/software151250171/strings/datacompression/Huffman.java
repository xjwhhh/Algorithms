package cn.edu.nju.software151250171.strings.datacompression;

import edu.princeton.cs.algs4.MinPQ;

/**
 * 霍夫曼压缩
 * 用较少的比特表示出现频率较高的字符，用较多的比特表示出现频率低的字符
 */
public class Huffman {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // Do not instantiate.
    private Huffman() {
    }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to standard output.
     * 压缩
     */
    public static void compress() {
        // read the input 读取输入
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // tabulate frequency counts 统计频率
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        // build Huffman trie 构造霍夫曼编码树
        Node root = buildTrie(freq);

        // build code table 构造编译表
        String[] st = new String[R];
        buildCode(st, root, "");

        // print trie for decoder 打印解码用的单词查找树
        writeTrie(root);

        // print number of bytes in original uncompressed message 打印字符总数
        BinaryStdOut.write(input.length);

        // use Huffman code to encode input 使用霍夫曼编码处理输入
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                } else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                } else throw new IllegalStateException("Illegal state");
            }
        }

        // close output stream
        BinaryStdOut.close();
    }

    // build the Huffman trie given frequencies
    //构造的第一步是创建一片由许多只有一个结点(即叶子结点)的树组成的森林，每棵树都表示输入流中的一个字符，
    // 每个结点中的freq变量的值都表示了它在输入流中的出现频率
    //首先找到两个频率最小的结点，然后创建一个以两者为子结点的新结点（新结点的频率值为两个子结点的频率值之和）
    //这个操作会将森林中树的数量减一，最终所有的结点会被合并为一棵单独的单词查找树
    //这棵树的叶子结点为所有待编码的字符和它们在输入中出现的频率，每个非叶子结点的频率值为两个子结点的频率值之和
    //频率较低的结点会被安排在树的底层，而高频率的结点会被安排在根结点附近的地方，根结点的频率值等于输入中的字符数量
    private static Node buildTrie(int[] freq) {

        // initialze priority queue with singleton trees 使用多棵单结点树初始化优先队列
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
            if (freq['\0'] == 0) pq.insert(new Node('\0', 0, null, null));
            else pq.insert(new Node('\1', 0, null, null));
        }

        // merge two smallest trees 合并两颗频率最小的树
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    // write bitstring-encoded trie to standard output
    //写入单词查找树，基于单词查找树的前序遍历
    //当它访问的是一个内部结点时，它会写入一个比特0；
    // 当它访问的是一个叶子结点时，他会写入一个比特1，紧接着是该叶子结点中字符的8位ASCII编码
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // make a lookup table from symbols and their encodings
    //使用单词查找树构造编译表
    //递归地遍历整棵树并为每个结点维护了一条从根结点到它的路径所对应的的二进制字符串（0表示左链接，1表示右链接）
    //每当达到一个叶子结点时，算法就将结点的编码设为该二进制字符串
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
        }
    }

    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     * 使用前缀码展开
     * 根据比特流的输入从根结点开始向下移动（读取一个比特，如果为0则移动到左子结点，如果为1则移动到右子结点）
     * 当遇到叶子结点后，输出该结点的字符并重新回到根结点
     */
    public static void expand() {

        // read in Huffman trie from input stream
        Node root = readTrie();

        // number of bytes to write
        int length = BinaryStdIn.readInt();

        // decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    //从比特字符串中构造单词查找树
    //首先读取一个比特以得到当前结点的类型
    // 如果是一个叶子结点（比特为1），读取字符的编码并创建一个叶子结点
    //如果是一个内部结点（比特为0），那么就创建一个内部结点并递归地继续构造它的左右子树
    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        } else {
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }
}
