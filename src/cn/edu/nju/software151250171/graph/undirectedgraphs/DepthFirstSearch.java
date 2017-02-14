package cn.edu.nju.software151250171.graph.undirectedgraphs;
/*
 * 深度优先搜索
 * 递归算法
 * 在访问其中一个顶点时：
 * 将它标记为已访问
 * 递归的访问它的所有没被标记过的邻居顶点
 */
public class DepthFirstSearch {
	private boolean[] marked;
	private int count;
	
	//找到和起点s连通的所有顶点
	public DepthFirstSearch (Graph G, int s){
		marked=new boolean[G.V()];
		dfs(G,s);
	}
	
	private void dfs(Graph G, int v){
		marked[v]=true;
		count++;
		for(int w:G.adj(v)){
			if(!marked[w]){
				dfs(G, w);
			}
		}
	}
	
	//v和s是连通的吗
	public boolean marked(int w){
		return marked[w];
	}
	
	//与s连通的顶点总数
	public int count(){
		return count;
	}
}
