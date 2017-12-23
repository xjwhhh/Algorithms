package cn.edu.nju.software151250171.base;

import java.util.Iterator;

/*
 * 背包，是一种不支持从中删除元素的数据类型，迭代的顺序不确定且与用例无关
 * 这份Bag的实现维护了一条链表，用于保存所有通过add()添加的元素
 */
public class Bag<Item> implements Iterable<Item> {
    private Node first;
    private int N;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {

        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
