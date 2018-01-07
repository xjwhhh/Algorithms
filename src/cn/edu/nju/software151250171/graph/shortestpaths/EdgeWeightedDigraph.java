package cn.edu.nju.software151250171.graph.shortestpaths;

import cn.edu.nju.software151250171.base.Bag;

import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.NEWLINE;

/*
 * 加权有向图的数据类型
 * 维护了一个由顶点索引的Bag对象的数组，Bag对象的内容为DirectedEdge对象
 * 每条边在邻接表中只会出现一次
 */
public class EdgeWeightedDigraph {
    private int V;                //顶点总数
    private int E;                //边的总数
    private Bag<DirectedEdge>[] adj;    //邻接表

    //创建一幅含有V个顶点的空图
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<DirectedEdge>();
        }
    }

    //图的顶点数
    public int V() {
        return V;
    }

    //图的边数
    public int E() {
        return E;
    }

    //向图中添加一条边e,每条边只会出现一次
    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    //和v相关联的所有边
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    //图的所有边
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v]) {
                bag.add(e);
            }
        }
        return bag;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
