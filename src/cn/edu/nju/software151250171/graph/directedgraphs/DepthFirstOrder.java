package cn.edu.nju.software151250171.graph.directedgraphs;

import cn.edu.nju.software151250171.base.Queue;
import cn.edu.nju.software151250171.base.Stack;

/*
 * 有向图中基于深度优先搜索的顶点排序
 * 该类允许用例用各种顺序遍历深度优先搜索经过的所有顶点
 */
public class DepthFirstOrder {
    private boolean marked[];
    private Queue<Integer> pre;           //所有顶点的前序排列，在递归调用之前将顶点加入队列
    private Queue<Integer> post;          //所有顶点的后序排列，在递归调用之后将顶点加入队列
    private Stack<Integer> reversePost;   //所有顶点的逆后序排列，在递归调用之后将顶点压入栈

    public DepthFirstOrder(Digraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
            post.enqueue(v);
            reversePost.push(v);
        }
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost();
    }
}
