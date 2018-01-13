package cn.edu.nju.software151250171.strings.datacompression;

/**
 * 游程编码
 * 适用于所有的比特字符串都是由交替出现的0和1组成的，只需要将游程的长度编码，比如位图
 * 随着分辨率的提高，游程编码的效果也会大大提高
 */
public class RunLength {
    private static final int R = 256;
    private static final int LG_R = 8;

    // Do not instantiate.
    private RunLength() {
    }

    /**
     * Reads a sequence of bits from standard input (that are encoded
     * using run-length encoding with 8-bit run lengths); decodes them;
     * and writes the results to standard output.
     * 读取一个游程的长度，将当前比特按照长度复制打印，转换当前比特然后继续，直到输入结束
     */
    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(LG_R);
            for (int i = 0; i < run; i++)
                BinaryStdOut.write(b);
            b = !b;
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input; compresses
     * them using run-length coding with 8-bit run lengths; and writes the
     * results to standard output.
     */
    public static void compress() {
        char run = 0;
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();//读取一个比特
            //如果与上一个比特不同，写入当前的计数值，并将计数器归零
            if (b != old) {
                BinaryStdOut.write(run, LG_R);
                run = 1;
                old = !old;
            }
            //如果与上一个比特相同且计数器已达到最大值，则写入最大值，再写入一个0计数值，然后将计数值归零
            else {
                if (run == R - 1) {
                    BinaryStdOut.write(run, LG_R);
                    run = 0;
                    BinaryStdOut.write(run, LG_R);
                }
                //增加计数器的值
                run++;
            }
        }
        BinaryStdOut.write(run, LG_R);
        BinaryStdOut.close();
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
