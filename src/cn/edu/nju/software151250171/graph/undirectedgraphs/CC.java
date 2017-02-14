package cn.edu.nju.software151250171.graph.undirectedgraphs;

/*
 * 这段Graph的用例使得它的用例可以独立处理一幅图中的每个连通分量
 * 这里的实现是基于一个由顶点索引的数组id[]。如果v属于第i个连通分量，则id[v]的值为i。
 * 构造函数会找出一个未被标记的顶点并调用递归函数dfs()来标记并区分出所有和它连通的顶点
 * 如此重复直到所有的顶点都被标记并区分
 */
public class CC {
	private boolean[] marked;
	private int[] id;   //标记属于第几个连通分量
	private int count;
	
	//预处理构造函数
	public CC(Graph G){
		marked=new boolean[G.V()];
		id=new int[G.V()];
		for(int s=0;s<G.V();s++){
			if(!marked[s]){
				dfs(G,s);
				count++;
			}
		}
	}
	
	private void dfs(Graph G,int v){
		marked[v]=true;
		id[v]=count;
		for(int w: G.adj(v)){
			if(!marked[w]){
				dfs(G, w);
			}
		}
	}
	
	//v和w连通吗
	public boolean connected(int v,int w){
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
