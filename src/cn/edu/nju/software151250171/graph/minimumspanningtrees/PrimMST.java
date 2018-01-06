package cn.edu.nju.software151250171.graph.minimumspanningtrees;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.sort.IndexMinPQ;

/**
 * Prim算法的即时实现
 * 只保存w到树顶点中权重最小的那条边，在将v添加到树中后键查是否需要更新这条权重最小的边（因为v-w的权重可能更小）
 * 只会在优先队列中保存每个非树顶点w的一条边：将它与树中的顶点连接起来的权重最小的那条边
 */
public class PrimMST {
    private Edge[] edgeTo;             //不在树中的顶点距离树最近的边
    private double[] distTo;           //distTo[w]=edgeTo[w].weight()
    private boolean[] marked;          //如果v在树中则为true
    private IndexMinPQ<Double> pq;     //有效的横切边，索引优先队列
    //优先队列中的最小键即是权重最小的横切边的权重，而与它相关联的顶点v就是下一个将被添加到树中的顶点

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<Double>(G.V());

        distTo[0] = 0.0;
        pq.insert(0, 0.0);              //用顶点0和权重0.0初始化pq
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());      //将最近的顶点添加到树中
        }
    }

    //将顶点v添加到树中，更新数据
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        //对于每个非树顶点w产生的变化只可能使得w到最小生成树的距离更近了
        //检查是否需要更新权重最小的边，遍历v的邻接链表
        for (Edge e : G.adj(v)) {
            int w = e.other(v);

            if (marked[w]) {   //v-w失效
                continue;
            }

            if (e.weight() < distTo[w]) {     //连接w和树的最佳边Edge变为e
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }
}
