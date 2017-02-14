package cn.edu.nju.software151250171.search.balancedsearchtrees;

/*
 * 理想情况下我们希望能够保持二分查找树的平衡性，需要一些灵活性，引入2-3查找树
 * 一棵完美平衡的2-3查找树中的所有空链接到根节点的距离都应该是相同的
 * 2-3查找树插入算法的根本在于变换都是局部的，除了相关的结点和链接之外不必修改或者检查树的其他部分
 * 这些局部变换不会影响树的全局有序性和平衡性
 */

/*
 * 红黑二叉查找树
 * 红链接将两个2-结点连接起来构成一个3-结点，黑链接则是2-3树中的普通链接
 * 对于任意的2-3树，只要对节点进行转换，我们都可以派生出一棵对应的二叉查找树
 * 红链接均为左链接
 * 没有任何一个节点同时与两条红链接相连
 * 该树是完美黑色平衡的，即任意空链接到跟结点的路径上的黑链接数量相同
 * 结合了二叉查找树中简洁高效的查找方法和2-3树中高效的平衡插入算法
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {

	private Node root;

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class Node {
		Key key; // 键
		Value value; // 相关联的值
		Node left, right; // 左右子树
		int N; // 这棵子树中的结点总数
		boolean color; // 由其父节点指向它的链接的颜色，红色为true,黑色为false,约定空链接为黑色

		Node(Key key, Value value, int N, boolean color) {
			this.key = key;
			this.value = value;
			this.N = N;
			this.color = color;
		}
	}

	private boolean isRed(Node x) {
		if (x == null) {
			return false;
		}
		return x.color == RED;
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

	// 旋转，将两个键中的较小者作为根节点变为将较大者作为根节点
	// 在插入新的键时我们可以使用旋转操作帮助我们保证2-3树和红黑树之间的一一对应关系
	Node rotateLeft(Node h) {
		Node xNode = h.right;
		h.right = xNode.left;
		xNode.left = h;
		xNode.color = h.color;
		h.color = RED;
		xNode.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return xNode;
	}

	Node rotateRight(Node h) {
		Node xNode = h.left;
		h.left = xNode.right;
		xNode.right = h;
		xNode.color = h.color;
		h.color = RED;
		xNode.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return xNode;
	}

	void flipColors(Node h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}

	// 根节点总是黑色

	/*
	 * 如果右子节点是红色的而左子节点是黑色的，进行左旋转 如果左子节点是红色的且它的左子节点也是红色的，进行右旋转 如果左右结点均为红色，进行颜色转换
	 */

	// 插入算法
	public void put(Key key, Value value) { // 查找key，找到则更新其值，否则为它新建一个节点
		root = put(root, key, value);
		root.color = BLACK;
	}

	private Node put(Node h, Key key, Value value) {
		if (h == null) { // 标准的插入操作，和父节点用红链接相连
			return new Node(key, value, 1, RED);
		}
		int cmp = key.compareTo(h.key);
		if (cmp < 0) {
			h.left = put(h.left, key, value);
		} else if (cmp > 0) {
			h.right = put(h.right, key, value);
		} else {
			h.value = value;
		}
		if (isRed(h.right) && !isRed(h.left)) {
			h = rotateLeft(h);
		}
		if (isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if (isRed(h.left) && isRed(h.right)) {
			flipColors(h);
		}
		h.N = size(h.left) + size(h.right);
		return h;
	}

	/*
	 * 为了保证我们不会删除一个2-结点，我们沿着左链接向下变换，确保当前节点不是2-结点 在沿着左链接向下的过程中，保证以下情况之一成立：
	 * 如果当前结点的左子节点不是2-结点，完成
	 * 如果当前结点的左子节点是2-结点而它的亲兄弟结点不是2-结点，将左子节点的兄弟结点中的一个键移动到左子节点中
	 * 如果当前节点的左子节点和它的亲兄弟结点都是2-结点，将左子节点，父节点中的最小建和左子节点最近的兄弟结点合并为一个4-结点，使父节点有3-
	 * 结点变为2-结点或者由4-结点变为3-结点
	 * 
	 */
}
