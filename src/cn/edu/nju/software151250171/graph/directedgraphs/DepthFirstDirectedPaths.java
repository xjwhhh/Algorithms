package cn.edu.nju.software151250171.graph.directedgraphs;

import cn.edu.nju.software151250171.base.Stack;

/**
 * 单点有向路径
 * 给定一幅有向图和一个起点s,回答从s给定目的地点v是否存在一条有向路径
 */

public class DepthFirstDirectedPaths {
    private boolean[] marked;  // marked[v] = true if v is reachable from s
    private int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
    private final int s;       // source vertex

    /**
     * Computes a directed path from {@code s} to every other vertex in digraph {@code G}.
     *
     * @param G the digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    //深度优先搜索
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a directed path from the source vertex {@code s} to vertex {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a directed path from the source
     * vertex {@code s} to vertex {@code v}, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    //是否存在路径
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a directed path from the source vertex {@code s} to vertex {@code v}, or
     * {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a directed path from the source vertex
     * {@code s} to vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    //路径
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}

