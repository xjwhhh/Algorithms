package cn.edu.nju.software151250171.search.symboltables;

import cn.edu.nju.software151250171.base.Queue;

import java.util.NoSuchElementException;

/**
 * 基于有序数组的二分查找 有两个数组，分别存放键和值
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int n;

    public BinarySearchST(int capacity) { // 调整数组大小
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) {
            return vals[i];
        } else {
            return null;
        }
    }

    public int rank(Key key) { // 计算小于给定键的键的数量,基于有序数组的二分查找（迭代）
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        int low = 0, high = n - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return low;
    }

    public void put(Key key, Value value) { // 查找键，找到则更新值，否则创建新的元素
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (value == null) {
            delete(key);
            return;
        }

        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) { // 给定的键存在于表中
            vals[i] = value;
            return;
        }
        for (int j = n; j > i; j--) { // 给定的键不存在于表中，将所有更大的键向后移动一位来腾出位置，并将给定的键值对分别插入到各自数组中的合适位置
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = value;
        n++;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    /**
     * Removes the largest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[n - 1];
    }

    public Key select(int k) {
        return keys[k];
    }

    public Key ceiling(Key key) { // 大于key的最小键
        int i = rank(key);
        return keys[i];
    }

    public Key floor(Key key) { //小于key的最大键
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == 0) return null;
        else return keys[i - 1];
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);

        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }

        n--;
        keys[n] = null;  // to avoid loitering
        vals[n] = null;

        // resize if 1/4 full
        if (n > 0 && n == keys.length / 4) resize(keys.length / 2);

    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new Queue<Key>();
        for (int i = rank(low); i < rank(high); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(high)) {
            queue.enqueue(keys[rank(high)]);
        }
        return queue;
    }

    //检查是否线性有序
    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i - 1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

}