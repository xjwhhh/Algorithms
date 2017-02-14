package cn.edu.nju.software151250171.graph.directedgraphs;

/*
 * 计算强连通分量的Kosaraju算法
 * 为了找到所有强连通分量，它会在反向图中进行深度优先搜索来将顶点排序（搜索顺序的逆后序）
 * 在给定有向图中用这个顺序再进行一次深度优先搜索
 */
public class KosarajuSCC {
	private boolean[] marked;  //已访问过的顶点
	private int[] id;   	   //强连通分量的标识符
	private int count;		   //强连通分量的数量
	
	//预处理构造函数
	public KosarajuSCC(Digraph G){
		marked=new boolean[G.V()];
		id=new int[G.V()];
		DepthFirstOrder order=new DepthFirstOrder(G.reverse());
		for(int s:order.reversePost()){
			if(!marked[s]){
				dfs(G,s);
				count++;
			}
		}
	}
	
	private void dfs(Digraph G,int v){
		marked[v]=true;
		id[v]=count;
		for(int w: G.adj(v)){
			if(!marked[w]){
				dfs(G, w);
			}
		}
	}
	
	//v和w连通吗
	public boolean stronglyConnected(int v,int w){
		return id[v]==id[w];
	}
	
	//v所在的连通分量标识符(0-count()-1)
	public int id(int v){
		return id[v];
	}
	
	//连通分量数
	public int count(){
		return count;
	}
}
