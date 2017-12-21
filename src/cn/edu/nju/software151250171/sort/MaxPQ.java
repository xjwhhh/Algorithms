package cn.edu.nju.software151250171.sort;

/**
 * 优先队列
 * 删除最大元素，插入元素
 * 上浮，下沉
 */

//堆实现的优先队列
public class MaxPQ<Key extends Comparable<Key>>{
	private Key[] pq;//基于堆的完全二叉树
	private int n=0;//储存pq[1...n],pq[0]没有使用
	
	public MaxPQ(int maxN){
		pq=(Key[])new Comparable[maxN+1];
	}
	
	public boolean isEmpty(){
		return n==0;
	}
	
	public int size(){
		return n;
	}
	
	private boolean less(int i,int j){
		return pq[i].compareTo(pq[j])<0;
	}
	
	private void exch(int i,int j){
		Key t=pq[i];
		pq[i]=pq[j];
		pq[j]=t;
	}
	
	//上浮,当子节点比父节点更大时，交换父子节点，这个节点比它的两个子节点都大（一个是曾经的父节点，另一个比它更小，因为它是曾经父节点的子节点）
	private void swim(int k){
		while(k>1&&less(k/2, k)){
			exch(k/2, k);
			k=k/2;
		}
	}
	
	//下沉，父节点比它的两个子节点或是其中一个更小，将父节点与子节点中的较大者交换
	private void sink(int k){
		while(2*k<=n){
			int j=2*k;
			if(j<n&&less(j, j+1)){
				j++;
			}//找较大的子节点
			if(!less(k, j)){
				break;
			}
			exch(k, j);
			k=j;
		}
	}
	
	public void insert(Key v){
		pq[++n]=v;
		swim(n);
	}
	
	public Key delMax(){
		Key max=pq[1]; //从根节点获得最大元素
		exch(1,n--);   //将其与最后一个节点交换
		pq[n+1]=null;  //防止对象游离
		sink(1);       //恢复堆的有序性
		return max;
	}
}
