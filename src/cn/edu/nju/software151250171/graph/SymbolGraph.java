package cn.edu.nju.software151250171.graph;

import cn.edu.nju.software151250171.search.ST;
/*
 * 符号图
 * 这个Graph实现允许用例用字符串代替数字索引来表示图中的顶点
 * 它维护了实例变量st（符号表用来映射顶点名和索引），keys(数组用来映射索引和顶点名）和G（使用索引表示顶点的图）
 * 为了构造这些数据结构，代码会将图的定义处理两遍（定义的每一行都包含一个顶点及它的相邻顶点列表，用分隔符sp隔开）
 */
public class SymbolGraph {
	private ST<String,Integer> st;  //符号名->索引
	private String[] keys;          //索引->符号名
	private Graph G;                //图
	
	public SymbolGraph(String stream,String sp){
		st=new ST<String,Integer>();
		In in=new In(stream);       //第一遍
		
		//构造索引
		while(in.hasNextLine()){
			String[] a= in.readLine().split(sp);  //读取字符串
			for(int i=0;i<a.length;i++){          //为每个不同的字符串关联一个索引
				if(!st.contains(a[i])){
					st.put(a[i],st.size());
				}
			}
		}
		
		keys=new String[st.size()];       //用来获得顶点名的反向索引是一个数组
		
		for(String name: st.keys()){
			keys[st.get(name)]=name;
		}
		
		G=new Graph(st.size());
		in=new In(stream);         //第二遍
		
		//构造图
		while(in.hasNextLine()){
			String[] a=in.readLine().split(sp);  //将每一行的第一个顶点和该行的其他顶点相连
			int v=st.get(a[0]);
			for(int i=1;i<a.length;i++){
				G.addEdge(v, st.get(a[i]));
			}
		}
	}
	
	public boolean contains(String s){
		return st.contains(s);
	}
	
	public int index(String s){
		return st.get(s);
	}
	
	public String name(int v){
		return keys[v];
	}
	
	public Graph G(){
		return G;
	}
}
