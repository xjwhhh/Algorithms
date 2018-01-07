package cn.edu.nju.software151250171.graph.shortestpaths;

/*
 * 任意顶点对之间的最短路径
 * 构造了DijkstraSP对象的数组，每个元素都将相应的定点作为起点
 * 在用例进行查询时，代码会访问起点所对应的单点最短路径对象并将目的顶点作为参数进行查询
 */
public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new DijkstraSP(G, v);
        }
    }

    Iterable<DirectedEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }

    double dist(int s, int t) {
        return all[s].distTo(t);
    }
}
