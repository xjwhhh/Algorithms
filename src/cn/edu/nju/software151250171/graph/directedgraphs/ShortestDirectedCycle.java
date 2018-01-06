package cn.edu.nju.software151250171.graph.directedgraphs;

import cn.edu.nju.software151250171.base.Stack;

/**
 * Finds a shortest directed cycle in a digraph.
 * Runs in O(V * (E + V)) time.
 *  run BFS from each vertex s.
 *  The shortest cycle through s is an edge v->s, plus a shortest path from s to v.
 */
public class ShortestDirectedCycle {
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)
    private int length;

    public ShortestDirectedCycle(Digraph G) {
        Digraph R = G.reverse();
        length = G.V() + 1;
        for (int v = 0; v < G.V(); v++) {
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(R, v);
            for (int w : G.adj(v)) {
                if (bfs.hasPathTo(w) && (bfs.distTo(w) + 1) < length) {
                    length = bfs.distTo(w) + 1;
                    cycle = new Stack<Integer>();
                    for (int x : bfs.pathTo(w))
                        cycle.push(x);
                    cycle.push(v);
                }
            }
        }
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public int length() {
        return length;
    }
}
