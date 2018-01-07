package cn.edu.nju.software151250171.graph.directedgraphs;

import cn.edu.nju.software151250171.graph.shortestpaths.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.EdgeWeightedDirectedCycle;

/*
 * 拓扑排序
 * 当且仅当一幅有向图是无环图时他才能进行拓扑排序
 * 一幅有向无环图的拓扑顺序即为所有顶点的逆后序排序
 * 在实际应用中，拓扑排序和有向环的检测总是会一起出现，因为有向环的检测是排序的前提
 */
public class Topological {
    private Iterable<Integer> order;

    public boolean hasOrder() {
        return order != null;
    }

    public Topological(Digraph G) {
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (!cyclefinder.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G);
            order = depthFirstOrder.reversePost();
        }
    }

    public Topological(EdgeWeightedDigraph G) {
//        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
//        if (!finder.hasCycle()) {
//            DepthFirstOrder dfs = new DepthFirstOrder(G);
//            order = dfs.reversePost();
//        }
    }


    //拓扑排序的所有顶点
    public Iterable<Integer> order() {
        return order;
    }

    //G是有向无环图吗
    public boolean isDAG() {
        return order != null;
    }
}

/* 解决任务调度通常需要以下三步：
 * 指明任务和优先级条件
 * 不断检测并去除有向图中的所有环，以确保存在可行方案
 * 使用拓扑排序解决调度问题
 * 类似的，调度方案的任何变动之后都需要再次检查是否存在环（使用DirectedCycle类），然后再计算
 * 新的调度安排（使用Toplogical类）
 */
