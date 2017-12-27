package cn.edu.nju.software151250171.search.hashtables;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.search.symboltables.SequentialSearchST;

/*
 * 散列表是算法在时间和空间上做出权衡的经典例子
 *
 * 第一步是用散列函数将被查找的键转化为数组的一个索引，第二步是处理碰撞冲突的过程，包括拉链法和线性探测法
 * 散列函数和键的类型有关，严格的说，对于每种类型的键我们都需要一个与之对应的散列函数
 *
 * java令所有数据类型都继承了一个能够返回一个32位比特整数的hashCode()方法
 *
 * 如果散列值的计算很耗时，那么我们或许可以将每个键的散列值缓存起来，即软缓存
 *
 * 散列方法需要满足：
 * 一致性——等价的键必然产生相等的散列值
 * 高效性——计算简便
 * 均匀性——均匀地散列所有的键
 * 但这样的散列方法实现是很困难的
 */

//基于拉链法的散列表
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int N;  //键值对总数
    private int M;  //散列表大小
    private SequentialSearchST<Key, Value>[] st;  //存放链表对象的数组

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {  //创建M条链表
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST();
        }
    }

    //自定义hash
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        return (Value) st[hash(key)].get(key);
    }

    public void put(Key key, Value value) {
        st[hash(key)].put(key, value);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    /*
     * 要删除一个键值对，先用散列值找到含有该键的SequentialSearchST对象，然后调用该对象的delete()方法
     */

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) N--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (M > INIT_CAPACITY && N <= 2 * M) resize(M / 2);
    }

}
