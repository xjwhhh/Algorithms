package cn.edu.nju.software151250171.graph.shortestpaths;

import cn.edu.nju.software151250171.base.Stack;

public class SP {
	private DirectedEdge[] edgeTo;   //由顶点索引的DirectedEdge对象的父链接数组
	private double[] distTo;            //到达起点的距离
	
	//从顶点s到v的距离，如果不存在则路径为无穷大
	public double distTo(int v){
		return distTo[v];
	}
	
	//是否存在从顶点s到v的路径
	public boolean hasPathTo(int v){
		return distTo[v]<Double.POSITIVE_INFINITY;
	}
	
	//从顶点s到v的路径，如果不存在则为null
	public Iterable<DirectedEdge> pathTo(int v){
		if(!hasPathTo(v)){
			return null;
		}
		Stack<DirectedEdge> path=new Stack<DirectedEdge>();
		for(DirectedEdge e=edgeTo[v];e!=null;e=edgeTo[e.from()]){
			path.push(e);
		}
		return path;
	}
	
	//松弛，检查最短路径
	private void relax(EdgeWeightedDigraph G,int v){
		for(DirectedEdge e: G.adj(v)){
			int w=e.to();
			if(distTo[w]>distTo[v]+e.weight()){
				distTo[w]=distTo[v]+e.weight();
				edgeTo[w]=e;
			}
		}
	}
}
