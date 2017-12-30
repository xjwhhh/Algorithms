package cn.edu.nju.software151250171.graph.undirectedgraphs;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.base.Stack;

/*
 * 广度优先搜索
 * 这段Graph的用例使用了广度优先搜索，以找出图中从构造函数得到的起点s到与其他所有顶点的最短路径
 * bfs()方法会标记所有与s连通的顶点，因此用例可以调用hasPathTo()来判定一个顶点与s是否连通
 * 并使用pathTo()得到一条从s到v的路径，确保没有其他从s到v的路径所含的边比这条路径更少
 */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  //到达该顶点的最短路径已知吗？
    private int[] edgeTo;      //到达该顶点的已知路径上的最后一个顶点
    private int s;             //起点
    private int[] distTo;

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;              //标记起点
        queue.enqueue(s);            //将它加入队列
        while (!queue.isEmpty()) {
            int v = queue.dequeue();   //从队列中删去下一顶点
            for (int w : G.adj(v)) {
                if (!marked[w]) {      //对于每个未被标记的相邻顶点
                    edgeTo[w] = v;     //保存最短路径的最后一条边
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;  //标记它，因为最短路径已知
                    queue.enqueue(w);//并将它添加到队列中
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    //返回从起点到给定的顶点的最短路径的长度，所需时间应该为常数
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
