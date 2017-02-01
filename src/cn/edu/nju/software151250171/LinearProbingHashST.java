package cn.edu.nju.software151250171;

import java.awt.RenderingHints.Key;

import javafx.scene.chart.ValueAxis;
import javafx.scene.layout.ColumnConstraints;

/*
 * 开放地址散列表
 * 
 * 线性探测法：当碰撞发生时（当一个键的散列值已经被另一个不同的键占用），我们直接检查散列表的下一个位置（将索引值加1）
 * 可能会产生三种结果：
 * 命中，该位置的键和被查找的键相同
 * 未命中，键为空（该位置没有键）
 * 继续查找，该位置的键和被查找的键不同
 * 
 * 在实现中使用了并行数组，一条保存键，一条保存值
 */

//基于线性探测的符号表
public class LinearProbingHashST <Key, Value> {
	private int N;              //符号表中键值对的总数
	private int M=16;           //线性探测表的大小
	private Key[] keys;         //键
	private Value[] values;     //值
	
	public LinearProbingHashST(){
		keys=(Key[]) new Object[M];
		values=(Value[]) new Object[M];
	}
	
	public LinearProbingHashST(int cap){
		keys=(Key[]) new Object[cap];
		values=(Value[]) new Object[cap];
	}
	
	//自定义hash
	private int hash(Key key){
		return (key.hashCode()&0x7fffffff)%M;
	}
	
	/**
	 * 保证散列表的使用率永远不会超过1/2
	 * 接受一个固定的容量作为参数，创建一个新的给定大小的LinearProbingHashST,
	 * 保存原表中的keys和values变量，然后将原表中的所有的键重新散列并插入到新表中
	 * @param cap
	 */
	
	//我们可以用相同的方法在拉链法中保持较短的链表（平均长度在2到8之间），但这并不是必须的
	private void resize(int cap){
		LinearProbingHashST<Key, Value> t;
		t=new LinearProbingHashST<Key,Value>(cap);
		for(int i=0;i<M;i++){
			if(keys[i]!=null){
				t.put(keys[i],values[i]);
			}
			keys=t.keys;
			values=t.values;
			M=t.M;
		}
	}
	
	/*
	 * 将键和值分别保存在两个数组中，使用空（标记为null）来表示一簇键的结束
	 */
	
	/**
	 * 如果一个新键的散列值是一个空元素，那么就将它保存在哪里；如果不是，我们就顺序查找一个空元素来保存它
	 * @param key
	 * @param value
	 */
	public void put(Key key,Value value){
		if(N>=M/2){
			resize(2*M); //将M加倍
		}
		
		int i;
		for(i=hash(key);keys[i]!=null;i=(i+1)%M){
			if(keys[i].equals(key)){
				values[i]=value;
				return;
			}
			keys[i]=key;
			values[i]=value;
			N++;
		}
	}
	
	/**
	 * 要查找一个键，我们从它的散列值开始顺序查找，如果找到则命中，如果遇到空元素则未命中
	 * @param key
	 * @return
	 */
	public Value get(Key key){
		for(int i=hash(key);keys[i]!=null;i=(i+1)%M){
			if(keys[i].equals(key)){
				return values[i];
			}
		}
		return null;
	}
	
	/**
	 * 不能直接将该键所在的位置设为null，因为这会使得在此位置之后的元素无法被查找
	 * 因此，我们需要将簇中被删除键的右侧的所有键重新插入散列表
	 * @param key
	 */
	public void delete(Key key){
		if(!contains(key)){
			return;
		}
		
		int i=hash(key);
		while(!key.equals(keys[i])){
			i=(i+1)%M;
		}
		
		keys[i]=null;
		values[i]=null;
		i=(i+1)%M;
		
		while(keys[i]!=null){
			Key keyToRedo=keys[i];
			Value valToRedo=values[i];
			keys[i]=null;
			values[i]=null;
			N--;
			put(keyToRedo, valToRedo);
			i=(i+1)%M;
		}
		
		N--;
		if(N>0&&N==M/8){
			resize(M/2);
		}
	}
	
	
	
	/*
	 * 散列表并非最好的，因为：
	 * 每种类型的键都需要一个优秀的散列函数
	 * 性能保证来自于散列函数的质量
	 * 散列函数的计算可能复杂而且昂贵
	 * 难以支持有序性相关的符号表操作
	 */
	
	
	
	
	
	
}
