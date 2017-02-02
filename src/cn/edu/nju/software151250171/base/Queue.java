package cn.edu.nju.software151250171.base;

import java.util.Iterator;

/*
 * 先进先出队列
 * 这份泛型的Queue实现的基础是两表数据结构，它可以用于创建任意数据类型的队列，支持迭代
 */
public class Queue <Item> implements Iterable<Item>{
	private Node first;  //指向最早添加的结点的链接
	private Node last;   //指向最近添加的节点的链接
	private int N;       //队列中的元素数量
	
	//定义了结点的嵌套类
	private class Node{
		Item item;
		Node next;
	}
	
	public boolean isEmpty(){
		return first==null;
	}
	
	public int size(){
		return N;
	}
	
	//向表尾添加元素
	public void enqueue(Item item){
		Node oldlast=last;
		last=new Node();
		last.item=item;
		last.next=null;
		if(isEmpty()){
			first=last;
		}
		else{
			oldlast.next=last;
		}
		N++;
	}
	
	//从表头删除元素
	public Item dequeue(){
		Item item=first.item;
		first=first.next;
		if(isEmpty()){
			last=null;
		}
		N--;
		return item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class ListIterator implements Iterator<Item>{
		private Node current=first;
		
		public boolean hasNext(){
			return current!=null;
		}
		
		public void remove(){
			
		}
		
		public Item next(){
			Item item=current.item;
			current=current.next;
			return item;
		}
	}

}
