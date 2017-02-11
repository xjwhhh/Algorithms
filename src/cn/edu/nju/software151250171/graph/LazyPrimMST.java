package cn.edu.nju.software151250171.graph;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.sort.MinPQ;

/*
 * Prim算法
 * 它的每一步都会为一棵生长中的树添加一条边。一开始这个树只有一个顶点，然后会向它添加V-1条边
 * 每次总是将下一条连接树中的顶点与不在树中的顶点且权重最小的边加入树中，即由树中的顶点所定义
 * 的切分中的一条横切边
 * 这种Prim算法的延时实现使用了一条优先队列来保存所有的横切边，一个由顶点索引的数组来标记树的
 * 顶点以及一条队列来保存最小生成树的边，这种延时实现会在优先队列中保留失效的边
 */
public class LazyPrimMST {
	private boolean[] marked;    //最小生成树的顶点
	private Queue<Edge> mst;     //最小生成树的边
	private MinPQ<Edge> pq;      //横切边（包括失效的边）
	
	public LazyPrimMST(EdgeWeightedGraph G){
		pq=new MinPQ<Edge>();
		marked=new boolean[G.V()];
		mst=new Queue<Edge>();
		
		visit(G, 0);             //假设G是连通的
		while(!pq.isEmpty()){
			Edge e=pq.delMin();  //从pq中得到权重最小的边
			
			int v=e.either();
			int w=e.other(v);
			if(marked[v]&&marked[w]){
				continue;  		 //跳过失效的边
			}
			mst.enqueue(e);      //将边添加到树中 
			if(!marked[v]){      //将顶点添加到树中
				visit(G, v);
			}
			if(!marked[w]){
				visit(G, w);
			}
		}
	}
	
	//标记顶点v并将所有连接v和未被标记顶点的边加入pq
	private void visit(EdgeWeightedGraph G,int v){
		marked[v]=true;
		for(Edge e:G.adj(v)){
			if(!marked[e.other(v)]){
				pq.insert(e);
			}
		}
	}
	
	public Iterable<Edge> edges(){
		return mst;
	}
	
//	public double weight(){		
//	}
}
