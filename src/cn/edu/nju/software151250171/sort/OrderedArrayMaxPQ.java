package cn.edu.nju.software151250171.sort;

import edu.princeton.cs.algs4.StdOut;

public class OrderedArrayMaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;          // elements
    private int n;             // number of elements

    // set inititial size of heap to hold size elements
    public OrderedArrayMaxPQ(int capacity) {
        pq = (Key[]) (new Comparable[capacity]);
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key delMax() {
        return pq[--n];
    }

    //从最后开始找适合插入的位置，将所有较大的元素向右移动一格以使数组保持有序
    public void insert(Key key) {
        int i = n - 1;
        while (i >= 0 && less(key, pq[i])) {
            pq[i + 1] = pq[i];
            i--;
        }
        pq[i + 1] = key;
        n++;
    }

    /***************************************************************************
     * Helper functions.
     ***************************************************************************/
    private boolean less(Key v, Key w) {
        return v.compareTo(w) < 0;
    }

    /***************************************************************************
     * Test routine.
     ***************************************************************************/
    public static void main(String[] args) {
        OrderedArrayMaxPQ<String> pq = new OrderedArrayMaxPQ<String>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while (!pq.isEmpty())
            StdOut.println(pq.delMax());
    }

}

