package cn.edu.nju.software151250171.search.symboltables;

/**
 * 基于无序链表的顺序查找 非常低效，无法满足处理庞大输入问题的需求
 */
public class SequentialSearchST<Key, Value> {
	private Node first; // 链表首节点

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
		for (Node xNode = first; xNode != null; xNode = xNode.next) {
			if (key.equals(xNode.key)) { // 不同的键有自带的equals方法
				return xNode.value; // 命中
			}
		}
		return null; // 未命中
	}

	public void put(Key key, Value value) { // 查找给定的键，找到则更新其值，否则在表中新建结点
		for (Node xNode = first; xNode != null; xNode = xNode.next) {
			if (key.equals(xNode.key)) {
				xNode.value = value;
				return; // 命中，更新
			} else {
				first = new Node(key, value, first); // 未命中，新建结点
			}
		}
	}
}
