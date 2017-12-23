package cn.edu.nju.software151250171.search.symboltables;

import cn.edu.nju.software151250171.base.Queue;

/**
 * 基于无序链表的顺序查找 非常低效，无法满足处理庞大输入问题的需求
 */
public class SequentialSearchST<Key, Value> {
    private Node first; // 链表首节点
    private int n;//链表长度

    private class Node { // 链表节点的定义
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public Value get(Key key) { // 查找给定的键，返回相关联的值
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (Node xNode = first; xNode != null; xNode = xNode.next) {
            if (key.equals(xNode.key)) { // 不同的键有自带的equals方法
                return xNode.value; // 命中
            }
        }
        return null; // 未命中
    }

    public void put(Key key, Value value) { // 查找给定的键，找到则更新其值，否则在表中新建结点，顺序查找
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (value == null) {
            delete(key);
            return;
        }
        for (Node xNode = first; xNode != null; xNode = xNode.next) {
            if (key.equals(xNode.key)) {
                xNode.value = value;
                return; // 命中，更新
            } else {
                first = new Node(key, value, first); // 未命中，新建结点
            }
        }
        n++;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    // Removes the specified key and its associated value from this symbol table,(if the key is in this symbol table).
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        first = delete(first, key);
    }

    //delete key in linked list beginning at Node x
    //太大的表会导致过多的调用
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }

}
