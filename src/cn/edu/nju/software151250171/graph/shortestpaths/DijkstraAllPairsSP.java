package cn.edu.nju.software151250171.graph.shortestpaths;

/*
 * 任意顶点对之间的最短路径
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
