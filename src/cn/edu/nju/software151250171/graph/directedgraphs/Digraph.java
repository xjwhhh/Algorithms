package cn.edu.nju.software151250171.graph.directedgraphs;

import cn.edu.nju.software151250171.base.Bag;
import edu.princeton.cs.algs4.In;

/*
 * 有向图
 * 与无向图基本相同，除了addEdge()方法只调用一次add()
 */
public class Digraph {
    private int V; // 顶点数目
    private int E; // 边的数目
    private Bag<Integer>[] adj; // 邻接表

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V]; // 创建邻接表
        for (int v = 0; v < V; v++) { // 将所有邻接表初始化为空
            adj[v] = new Bag<Integer>();
        }
    }

	public Digraph(In in) {
		this(in.readInt()); // 读取V并将图初始化
		int E = in.readInt(); // 读取E
		for (int i = 0; i < E; i++) { // 添加一条边
			int v = in.readInt(); // 读取一个顶点
			int w = in.readInt(); // 读取另一个顶点
			addEdge(v, w); // 添加一条连接它们的边
		}
	}

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w); // 将w添加到v的链表中
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // 返回有向图的一个副本，但将其中所有边的方向反转，找出指向每个顶点的所有边
    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);
            }
        }
        return R;
    }

    // 计算v的度数
    public static int degree(Digraph G, int v) {
        int degree = 0;
        for (int w : G.adj[v]) {
            degree++;
        }
        return degree;
    }

    // 计算所有顶点的最大度数
    public static int maxDegree(Digraph G) {
        int max = 0;
        for (int v = 0; v < G.V; v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    // 计算所有顶点的平均度数
    public static double avgDegree(Digraph G) {
        return 2.0 * G.E() / G.V();
    }

    // 计算自环的个数
    public static int numberOfSelfLoops(Digraph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    count++;
                }
            }
        }
        return count;
    }

    // 图的邻接表的字符串表示（Digraph的实例方法）
    public String toString() {
        String s = V + "vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : this.adj(v)) {
                s += w + " ";
                s += "\n";
            }
        }
        return s;
    }
}
