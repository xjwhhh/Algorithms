package cn.edu.nju.software151250171.graph;

/*
 * 检测给定的图是不是无环图
 * 假设不存在自环或平行边
 */
public class Cycle {
	private boolean[] marked;
	private boolean hasCycle;
	
	public Cycle(Graph G){
		marked=new boolean[G.V()];
		for(int s=0;s<G.V();s++){
			if(!marked[s]){
				dfs(G,s,s);
			}
		}
	}
	
	public void dfs(Graph G,int v,int u){
		marked[v]=true;
		for(int w: G.adj(v)){
			if(!marked[w]){
				dfs(G, w, v);
			}
			else if(w!=u){
				hasCycle=true;
			}
		}
	}
	
	public boolean hasCycle(){
		return hasCycle;
	}
}
