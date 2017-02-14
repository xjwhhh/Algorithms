package cn.edu.nju.software151250171.graph.undirectedgraphs;

import cn.edu.nju.software151250171.base.Stack;

/*
 * 单点路径问题
 * 使用深度优先搜索查找图中的路径，以找出图中从给定的起点s到它连通的所有顶点的路径。
 * 为了保存到达每个顶点的已知路径，这段代码使用了一个以顶点编号为索引的数组edgeTo[]，edgeTo[w]=v
 * 表示v-w是第一次访问w时经过的边。edgeTo[]数组是一颗用父链表示的以s为根且含有所有与s连通的顶点的树
 */
public class DepthFirstPath {
	private boolean[] marked;  //这个顶点上调用过dfs()了吗
	private int[] edgeTo;      //从这个起点到一个顶点的已知路径上的最后一个顶点，是一棵由父链接表示的树
	private int s;             //起点
	
	//在G中找出所有起点为s的路径
	public DepthFirstPath(Graph G,int s){
		marked=new boolean[G.V()];
		edgeTo=new int[G.V()];
		this.s=s;
		dfs(G,s);
	}
	
	private void dfs(Graph G, int v){
		marked[v]=true;
		for(int w: G.adj(v)){
			if(!marked[w]){
				edgeTo[w]=v;
				dfs(G, w);
			}
		}
	}
	
	//是否存在从s到v的路径
	public boolean hasPathTo(int v){
		return marked[v];
	}
	
	//s到v的路径，如果不存在则返回null
	public Iterable<Integer> pathTo(int v){
		if(!hasPathTo(v)){
			return null;
		}
		Stack<Integer> path=new Stack<Integer>();
		for(int x=v;x!=s;x=edgeTo[x]){
			path.push(x);
		}
		path.push(s);
		return path;
	}
}
