//package cn.edu.nju.software151250171.graph.shortestpaths;
//
//import cn.edu.nju.software151250171.base.Queue;
//import cn.edu.nju.software151250171.base.Stack;
//import cn.edu.nju.software151250171.graph.Edge;
//
///*
// * 基于队列的Bellman-Ford算法
// * 队列中不出现重复的顶点
// * 在一轮中，改变了的edgeTo[]和distTo[]的值的所有顶点都会在下一轮中处理
// * Bellman-Ford算法的实现修改了relax()方法，将被成功放松的边指向的所有顶点加入到一条FIFO
// * 队列中（队列中不出现重复的顶点）并周期性的检查edgeTo[]表示的子图中是否存在负权重环
// */
//public class BellmanFordSP {
//	private double[] distTo;                  //从起点到某个顶点的长度
//	private DirectedEdge[] edgeTo;			  //从起点到某个定点的最后一条边
//	private boolean[] onQ;					  //该顶点是否存在于队列中
//	private Queue<Integer> queue;			  //正在被放松的顶点
//	private int cost;						  //relax()的调用次数
//	private Iterable<DirectedEdge> cycle;     //edgeTo[]中是否有负权重环
//
//	public BellmanFordSP(EdgeWeightedDigraph G,int s){
//		distTo=new double[G.V()];
//		edgeTo=new DirectedEdge[G.V()];
//		onQ=new boolean[G.V()];
//		queue=new Queue<Integer>();
//
//		for(int v=0;v<G.V();v++){
//			distTo[v]=Double.POSITIVE_INFINITY;
//		}
//		distTo[s]=0.0;
//		queue.enqueue(s);
//		onQ[s]=true;
//		while(!queue.isEmpty()&&!hasNegativeCycle()){
//			int v=queue.dequeue();
//			onQ[v]=false;
//			relax(G,v);
//		}
//	}
//
//	private void relax(EdgeWeightedDigraph G,int v){
//		for (DirectedEdge e : G.adj(v)) {
//			int w = e.to();
//			if (distTo[w] > distTo[v] + e.weight()) {
//				distTo[w] = distTo[v] + e.weight();
//				edgeTo[w] = e;
//				if(!onQ[w]){
//					queue.enqueue(w);
//					onQ[w]=true;
//				}
//			}
//			if(cost++%G.V()==0){      //每调用V次relax()方法后即调用findNegativeCycle()方法
//				findNegativeCycle();  //如果找到负权重环则停止运行
//			}
//		}
//	}
//
//	// 从顶点s到v的距离，如果不存在则路径为无穷大
//	public double distTo(int v) {
//		return distTo[v];
//	}
//
//	// 是否存在从顶点s到v的路径
//	public boolean hasPathTo(int v) {
//		return distTo[v] < Double.POSITIVE_INFINITY;
//	}
//
//	// 从顶点s到v的路径，如果不存在则为null
//	public Iterable<DirectedEdge> pathTo(int v) {
//		if (!hasPathTo(v)) {
//			return null;
//		}
//		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
//		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
//			path.push(e);
//		}
//		return path;
//	}
//
//	private void findNegativeCycle(){
//		int V=edgeTo.length;
//		EdgeWeightedDigraph spt=new EdgeWeightedDigraph(V);
//		for(int v=0;v<V;v++){
//			if(edgeTo[v]!=null){
//				spt.addEdge(edgeTo[v]);
//			}
//		}
//		EdgeWeightedCycleFinder cf=new EdgeWeightedCycleFinder();
//
//		cycle=cf.cycle();
//	}
//
//	//判断是否存在从起点可达的负权重环
//	public boolean hasNegativeCycle(){
//		return cycle!=null;
//	}
//
//	public Iterable<DirectedEdge> negativeCycle(){
//		return cycle;
//	}
//}
