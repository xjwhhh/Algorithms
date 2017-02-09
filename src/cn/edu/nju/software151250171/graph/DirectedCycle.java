package cn.edu.nju.software151250171.graph;

import cn.edu.nju.software151250171.base.Stack;

/*
 * 寻找有向环
 * 该类为标准的递归dfs()方法添加了一个布尔类型的数组onStack[]来保存递归调用期间栈上的所有顶点
 * 当他找到一条边v->w且w在栈中时，他就找到了一个有向环，环上的所有顶点可以通过edgeTo[]中的链接得到
 */
public class DirectedCycle {
	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;
	private boolean[] onstack;
	
	public DirectedCycle(Digraph G){
		onstack=new boolean[G.V()];
		edgeTo=new int[G.V()];
		marked=new boolean[G.V()];
		for(int v=0;v<G.V();v++){
			if(!marked[v]){
				dfs(G,v);
			}
		}
	}
	
	public void dfs(Digraph G, int v){
		onstack[v]=true;
		marked[v]=true;
		for(int w:G.adj(v)){
			if(this.hasCycle()){
				return;
			}
			else if(!marked[w]){
				edgeTo[w]=v;
				dfs(G, w);
			}
			else if(onstack[w]){
				cycle=new Stack<Integer>();
				for(int x=v;x!=w;x=edgeTo[x]){
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
		onstack[v]=false;
	}
	
	public boolean hasCycle(){
		return cycle!=null;
	}
	
	public Iterable<Integer> cycle(){
		return cycle;
	}
}
