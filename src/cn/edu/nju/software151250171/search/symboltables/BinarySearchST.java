package cn.edu.nju.software151250171.search.symboltables;

import cn.edu.nju.software151250171.base.Queue;

/**
 * 基于有序数组的二分查找 有两个数组，分别存放键和值
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
	private Key[] keys;
	private Value[] vals;
	private int N;

	public BinarySearchST(int capacity) { // 调整数组大小
		keys = (Key[]) new Comparable[capacity];
		vals = (Value[]) new Object[capacity];
	}

	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public Value get(Key key) {
		if (isEmpty()) {
			return null;
		}
		int i = rank(key);
		if (i < N && keys[i].compareTo(key) == 0) {
			return vals[i];
		} else {
			return null;
		}
	}

	public int rank(Key key) { // 计算小于给定键的键的数量,基于有序数组的二分查找（迭代）
		int low = 0, high = N - 1;
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
		int i = rank(key);
		if (i < N && keys[i].compareTo(key) == 0) { // 给定的键存在于表中
			vals[i] = value;
			return;
		}
		for (int j = N; j > i; j--) { // 给定的键不存在于表中，将所有更大的键向后移动一位来腾出位置，并将给定的键值对分别插入到各自数组中的合适位置
			keys[j] = keys[j - 1];
			vals[j] = vals[j - 1];
		}
		keys[i] = key;
		vals[i] = value;
		N++;
	}

	public Key min() {
		return keys[0];
	}

	public Key max() {
		return keys[N - 1];
	}

	public Key select(int k) {
		return keys[k];
	}

	public Key ceiling(Key key) { // 大于key的最小键
		int i = rank(key);
		return keys[i]; // 如果这个键刚好已经在表里有一份了，那返回的不就是自己吗?
	}

	// public Key floor(Key key){ //小于key的最大键
	// return
	// }

	public void delete(Key key) {
		put(key, null);
	}
	
	public boolean contains(Key key){
		return get(key)!=null;
	}

	public Iterable<Key> keys(Key low,Key high){
		Queue<Key> queue=new Queue<Key>();
		for(int i=rank(low);i<rank(high);i++){
			queue.enqueue(keys[i]);
			}
		if(contains(high)){
			queue.enqueue(keys[rank(high)]);
		}
		return queue;
	}
}