package cn.edu.nju.software151250171.graph;

import cn.edu.nju.software151250171.base.Bag;

/*
 * 加权无向图的数据类型
 */
public class EdgeWeightedGraph {
	private int V;				//顶点总数
	private int E;				//边的总数
	private Bag<Edge>[] adj;	//邻接表
	
	//创建一幅含有V个顶点的空图
	public EdgeWeightedGraph(int V){
		this.V=V;
		this.E=0;
		adj=(Bag<Edge>[]) new Bag[V];
		for(int v=0;v<V;v++){
			adj[v]=new Bag<Edge>();
		}
	}
	
	//图的顶点数
	public int V(){
		return V;
	}
	
	//图的边数
	public int E(){
		return E;
	}
	
	//向图中添加一条边e，每条边都会出现两次，在两个顶点的邻接表中
	public void addEdge(Edge e){
		int v=e.either();
		int w=e.other(v);
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	
	//和v相关联的所有边
	public Iterable<Edge> adj(int v){
		return adj[v];
	}
	
	//图的所有边
	public Iterable<Edge> edges(){
		Bag<Edge> bag=new Bag<Edge>();
		for(int v=0;v<V;v++){
			for(Edge e:adj[v]){
				if(e.other(v)>v){
					bag.add(e);
				}
			}
		}
		return bag;
	}
}
