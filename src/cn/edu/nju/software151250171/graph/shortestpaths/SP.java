package cn.edu.nju.software151250171.graph.shortestpaths;

import cn.edu.nju.software151250171.base.Stack;

public class SP {
    private DirectedEdge[] edgeTo;
    //由顶点索引的DirectedEdge对象的父链接数组，
    //其中edgeTo[v]的值为树中连接v和它的父节点的边(也是从s到v的最短路径上的最后一条边)
    private double[] distTo;
    //到达起点的距离，distTo[v]为从s到v的已知最短路径的长度

    //从顶点s到v的距离，如果不存在则路径为无穷大
    public double distTo(int v) {
        return distTo[v];
    }

    //是否存在从顶点s到v的路径
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    //从顶点s到v的路径，如果不存在则为null
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    //松弛，检查最短路径
    //放松边v->w意味着检查从s到w的最短路径是否是先从s到v，然后再由v到w。如果是，则根据这个情况更新数据结构的内容。
    //由v到达w的最短路径是distTo[v]与e.weight()之和——如果这个值不小于distTo[w]，则这条边失效了并将它忽略，如果这个值更小，就更新数据
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
}
