package cn.edu.nju.software151250171.graph;

/*
 * 顶点对的可达性
 * 是否存在一条从一个给定的顶点v到另一个给定的顶点w的路径
 */
public class TransitiveClosure {
	private DirectedDFS[] all;
	
	public TransitiveClosure(Digraph G) {
		all=new DirectedDFS[G.V()];
		for(int v=0;v<G.V();v++){
			all[v]=new DirectedDFS(G, v);
		}
	}
	
	public boolean reachable(int v,int w){
		return all[v].marked(w);
	}
}
