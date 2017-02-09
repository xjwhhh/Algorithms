package cn.edu.nju.software151250171.graph;

/*
 * 有向图的可达性
 * 是否存在一条从s到达给定顶点v的有向路径
 */
public class DirectedDFS {
	private boolean[] marked;
	
	//在G中找到从s可达的所有顶点
	public DirectedDFS(Digraph G,int s){
		marked=new boolean[G.V()];
		dfs(G,s);
	}
	
	//在G中找到从sources中的所有顶点可达的所有顶点
	public DirectedDFS(Digraph G,Iterable<Integer> sources){
		marked=new boolean[G.V()];
		for(int s:sources){
			if(!marked[s]){
				dfs(G,s);
			}
		}
	}
	
	//深度优先搜索
	public void dfs(Digraph G,int v){
		marked[v]=true;
		for(int w:G.adj(v)){
			if(!marked[w]){
				dfs(G, w);
			}
		}
	}
	
	//v是可达的吗
	public boolean marked(int v){
		return marked[v];
	}
	
	
}
