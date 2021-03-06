package cn.edu.nju.software151250171.graph.shortestpaths;

import cn.edu.nju.software151250171.base.Stack;
import cn.edu.nju.software151250171.sort.IndexMinPQ;

/*
 * 最短路径的Dijkstra算法
 * 每次都会为最短路径树添加一条边，该边由一个树中的顶点指向一个非树顶点w且它是到s最近的顶点
 * 即将distTo[]最小的非树顶点放松并加入树中
 */
public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;//优先队列，保存需要被放松的顶点并确认下一个被放松的顶点

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
    }

    //要么边的得到的顶点还不在优先队列中，此时需要使用insert()方法将它加入到优先队列中
    //要么它已经在优先队列中且优先级需要被降低，此时可以使用change()实现
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    // 从顶点s到v的距离，如果不存在则路径为无穷大
    public double distTo(int v) {
        return distTo[v];
    }

    // 是否存在从顶点s到v的路径
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // 从顶点s到v的路径，如果不存在则为null
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
