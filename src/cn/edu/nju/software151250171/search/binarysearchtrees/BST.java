package cn.edu.nju.software151250171.search.binarysearchtrees;

import cn.edu.nju.software151250171.base.Queue;
import edu.princeton.cs.algs4.StdOut;

/*
 * 二叉查找树
 * 每个节点的键都大于其左子树中任意结点的键而小于其右子树中的任意结点的键
 */

/**
 * 基于二叉查找树的符号表
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key; // 键
        private Value value; // 值
        private Node left, right; // 指向子树的链接
        private int N; // 以该节点为根的子树中的结点总数

        public Node(Key key, Value value, int N) {
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) { // 在以x为根节点的子树中查找并返回key所对应的值
        if (x == null) { // 如果找不到就返回null
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) { // 查找key,找到则更新它的值，否则为它创建一个新的节点
        root = put(root, key, value);
    }

    // 如果key存在于以x为根节点的子树中则更新它的值
    // 否则将以key和value为键值对的新节点插入到该子树中
    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /**
     * 如果给定的键key小于二叉查找树的根节点的键，那么小于等于key的最大键floor(key)一定在根节点的左子树中；
     * 如果给定的键key大于等于二叉查找树的根节点的键，那么只有当根节点右子树中存在小于等于key的节点时，小于等于key的最大键才会出现在右子树中，
     * 否则根节点就是小于等于key的最大键
     *
     * @param x
     * @param key
     * @return
     */
    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        } else if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        } else if (cmp > 0) {
            return ceiling(x.right, key);
        }
        Node t = ceiling(x.left, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    /**
     * 返回排名为k的节点
     * 如果左子树中的结点数t大于k，就递归的在左子树中查找排名为k的键
     * 如果t等于k，就返回根结点中的值
     * 如果t小于k，就递归的在右子树中查找排名为(k-t-1)的值
     *
     * @param x
     * @param k
     * @return
     */
    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    /**
     * 返回以x为根节点的子树中小于x.key的键的数量
     * 如果给定的键和根结点的键相等，返回左子树中的结点总数t
     * 如果给定的键小于根节点，返回该键在左子树中的排名
     * 如果给定的键大于根节点，返回t+1加上他在右子树中的排名
     *
     * @param key
     * @param x
     * @return
     */
    private int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        // 不断深入根节点的左子树直至遇见一个空链接，然后将指向该节点的链接指向该节点的右子树，此时没有任何链接指向要删除的结点，被删除的节点会被垃圾收集器清理掉
        // 个人理解为将最小节点的右子树变为父节点的左子树，子节点被删除（子节点是没有左子树的，因为是最小的），右子树不确定
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    /**
     * 一个缺陷是可能会在某些实际应用中产生性能问题，这个问题在于后继结点是一个随意的决定，且没有考虑树的对称性
     *
     * @param x
     * @param key
     * @return
     */
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x; // t为即将被删除的结点
            x = min(t.right); // x为t的后继结点，即右子树中的最小节点，要用x替换t
            x.right = deleteMin(t.right); // x的右子树为t的右子树删除x后的树
            x.left = t.left; // x的左子树为t的左子树
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    // 二叉查找树的范围查找操作
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, low, high);
        return queue;
    }

    /**
     * 将所有落在给定范围内的键加入一个队列Queue并跳过那些不可能含有所查找键的子树
     * 来实现接受两个参数并能够将给定范围内的键返回给用例的keys()方法
     * 递归的查找根结点的左子树，然后查找根结点，然后查找根结点的左子树
     *
     * @param x
     * @param queue
     * @param low
     * @param high
     */
    private void keys(Node x, Queue<Key> queue, Key low, Key high) {
        if (x == null) {
            return;
        }
        int cmplow = low.compareTo(x.key);
        int cmphigh = high.compareTo(x.key);
        if (cmplow < 0) {
            keys(x.left, queue, low, high);
        }
        if (cmplow <= 0 && cmphigh >= 0) {
            queue.enqueue(x.key);
        }
        if (cmphigh > 0) {
            keys(x.right, queue, low, high);
        }
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * 非递归的put与get
     *
     * @param key
     * @param val
     * @param test
     */
    public void put(Key key, Value val, int test) {
        Node z = new Node(key, val, 1);
        if (root == null) {
            root = z;
            return;
        }

        //保存一个指向底层结点的链接，以便使之成为新结点的父结点
        //还需要额外便利一次查找路径来更新所有的结点计数器以保证结点插入的准确性
        Node parent = null, x = root;
        while (x != null) {
            parent = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else {
                x.value = val;
                return;
            }
        }
        int cmp = key.compareTo(parent.key);
        if (cmp < 0) parent.left = z;
        else parent.right = z;
    }

    Value get(Key key, int test) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    //接受一个结点Node作为参数，若该结点是一个二叉查找树的根结点则返回true，否则返回false
    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    //takes a Node as argument and returns true if the subtree count field N is consistent in the data structure rooted at that node, false otherwise.
    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (size(x) != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    //对于0到size()-1之间的所有i，检查i和rank(select(i))是否相等，并检查二叉查找树中的任意键key和select(rank(key))是否相等
    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }
}
