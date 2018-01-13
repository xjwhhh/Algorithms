package cn.edu.nju.software151250171.graph.shortestpaths;

import cn.edu.nju.software151250171.base.Stack;
import cn.edu.nju.software151250171.graph.directedgraphs.Topological;

/*
 * 无环加权有向图的最短路径算法
 * 因为是按照拓扑顺序处理无环有向图中的顶点，所以不可能遇到已经被放松过的顶点
 */
public class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        Topological top = new Topological(G);
        for (int v : top.order()) {
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
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
