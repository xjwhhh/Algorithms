package cn.edu.nju.software151250171.graph.minimumspanningtrees;

import cn.edu.nju.software151250171.base.Bag;
import cn.edu.nju.software151250171.base.UF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 与Kruskal算法类似，只是分阶段的向一组森林里逐渐添加来构造一棵最小生成树
 * 在每个阶段中，找出所有连接两棵不同的树的权重最小的边，并将它们全部加入最小生成树
 * 为了避免出现环，假设所有边的权重均不相同
 * 维护一个由顶点索引的数组来辨别连接每棵树和它最近的邻居的边
 */
public class BoruvkaMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Bag<Edge> mst = new Bag<>();    // edges in MST
    private double weight;                      // weight of MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     */
    public BoruvkaMST(EdgeWeightedGraph G) {
        UF uf = new UF(G.V());

        // repeat at most log V times or until we have V-1 edges
        for (int t = 1; t < G.V() && mst.size() < G.V() - 1; t = t + t) {

            // foreach tree in forest, find closest edge
            // if edge weights are equal, ties are broken in favor of first edge in G.edges()
            Edge[] closest = new Edge[G.V()];
            for (Edge e : G.edges()) {
                int v = e.either(), w = e.other(v);
                int i = uf.find(v), j = uf.find(w);
                if (i == j) continue;   // same tree
                if (closest[i] == null || less(e, closest[i])) closest[i] = e;
                if (closest[j] == null || less(e, closest[j])) closest[j] = e;
            }

            // add newly discovered edges to MST
            for (int i = 0; i < G.V(); i++) {
                Edge e = closest[i];
                if (e != null) {
                    int v = e.either(), w = e.other(v);
                    // don't add the same edge twice
                    if (!uf.connected(v, w)) {
                        mst.add(e);
                        weight += e.weight();
                        uf.union(v, w);
                    }
                }
            }
        }

        // check optimality conditions
        assert check(G);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     *
     * @return the edges in a minimum spanning tree (or forest) as
     * an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     *
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    // is the weight of edge e strictly less than that of edge f?
    private static boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }

    /**
     * Unit tests the {@code BoruvkaMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        BoruvkaMST mst = new BoruvkaMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}
