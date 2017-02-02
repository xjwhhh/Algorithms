package cn.edu.nju.software151250171.base;

import java.util.Iterator;

import javafx.scene.Node;

public class Stack <Item> implements Iterable<Item>{
	private Node first;  //栈顶，最近添加的元素
	private int N;       //元素数量
	
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
	
	//向栈顶添加元素
	public void push(Item item){
		Node oldfirst=first;
		first=new Node();
		first.item=item;
		first.next=oldfirst;
		N++;
	}
	
	//从栈顶删除元素
	public Item pop(){
		Item item=first.item;
		first=first.next;
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
