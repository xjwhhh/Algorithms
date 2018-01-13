package cn.edu.nju.software151250171.graph.shortestpaths;

import edu.princeton.cs.algs4.StdIn;

/*
 * 优先级限制下的并行任务调度问题的关键路径方法
 * 能够将任意人物调度问题转化为无环加权有向图中的一个最长路径问题，用AcyclicLp解决它
 * 并打印出每个任务的开始时间以及调度方案的结束时间
 */
public class CPM {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        StdIn.readLine();
        EdgeWeightedDigraph G;
        G = new EdgeWeightedDigraph(2 * N + 2);
        int s = 2 * N;
        int t = 2 * N + 1;

        for (int i = 0; i < N; i++) {
            String[] a = StdIn.readLine().split("\\s+");
            double duration = Double.parseDouble(a[0]);
            G.addEdge(new DirectedEdge(i, i + N, duration));
            G.addEdge(new DirectedEdge(s, i, 0.0));

            G.addEdge(new DirectedEdge(i + N, t, 0.0));
            for (int j = 1; j < a.length; j++) {
                int successor = Integer.parseInt(a[j]);
                G.addEdge(new DirectedEdge(i + N, successor, 0.0));
            }
        }
        AcyclicSP lp = new AcyclicSP(G, s);

        System.out.println("Start times");
        for (int i = 0; i < N; i++) {
            System.out.printf("%4d: %5.1f\n", i, lp.distTo(i));
        }
        System.out.printf("Finish time:%5.1f\n", lp.distTo(t));
    }
}

