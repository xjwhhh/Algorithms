package cn.edu.nju.software151250171.base;

import java.util.Iterator;

/*
 * 下压（LIFO）栈（能够动态调整数组大小的实现）
 * 这份泛型的可迭代的Stack API的实现是所有集合类抽象数据类型实现的模板
 * 它将所有元素保存在数组中，并动态调整数组的大小以保持数组大小和栈大小之比小于一个常数
 */
public class ResizingArrayStack<Item> implements Iterable<Item>{
	private Item[] a=(Item[]) new Object[1];  //栈元素
	private int N=0;                          //元素数量
	
	public boolean isEmpty(){
		return N==0;
	}
	
	public int Size(){
		return N;
	}
	
	//将栈移动到一个大小为max的新数组
	private void resize(int max){
		Item[] temp=(Item[]) new Object[max];
		for(int i=0;i<N;i++){
			temp[i]=a[i];
		}
		a=temp;
	}
	
	//将元素添加到栈顶
	public void push(Item item){
		if(N==a.length){
			resize(2*a.length);
		}
		a[N++]=item;
	}
	
	//从栈顶删除元素
	public Item pop(){
		Item item=a[--N];
		a[N]=null;  //避免对象游离
		if(N>0&&N==a.length/4){
			resize(a.length/2);
		}
		return item;
	}

 	
	
	@Override
	public Iterator<Item> iterator() {
		return new ReverseArrayIterator();
	}
	
	//支持后进先出的迭代
	private class ReverseArrayIterator implements Iterator<Item>{
		private int i=N;
		
		public boolean hasNext(){
			return i>0;
		}
		
		public Item next(){
			return a[--i];
		}
		
		public void remove(){
			
		}
	}
 
}
