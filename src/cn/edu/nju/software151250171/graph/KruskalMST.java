package cn.edu.nju.software151250171.graph;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.base.UF;

/*
 * 最小生成树中的Kruskal算法
 * 按照边的权重顺序（从小到大）处理它们，将边加入最小生成树中，加入的边不会与已经加入的边构成环，
 * 直到树中含有V-1条边为止
 * 使用了一条队列来保存最小生成树中的所有边，一条优先队列来保存还未被检查的边和一个union-find
 * 的数据结构来判断无效的边。最小生成树的所有边会按照权重的升序返回给用例
 */
public class KruskalMST {
	private Queue<Edge> mst;
	
	public KruskalMST(EdgeWeightedGraph G){
		mst=new Queue<Edge>();
		MinPQ<Edge> pq=new MinPQ<Edge>();
		for(Edge e:G.edges()){
			pq.insert(e);
		}
		UF uf=new UF(G.V());
		
		while(!pq.isEmpty()&&mst.size()<G.V()-1){
			Edge e=pq.delMin();         //从pq得到权重最小的边和它的顶点
			int v=e.either();
			int w=e.other(v);
			if(uf.connected(v,w)){      //忽略失效的边
				continue;
			}
			uf.union(v,w);              //合并分量
			mst.enqueue(e);             //将边添加到最小生成树中
		}
	}
	
	public Iterable<Edge> edges(){
		return mst;
	}
	
//	public double weight()
	
}
