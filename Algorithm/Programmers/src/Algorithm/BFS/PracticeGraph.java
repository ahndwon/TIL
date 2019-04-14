package Algorithm.BFS;

import java.util.LinkedList;

public class PracticeGraph {
    private int V;
    private LinkedList<Integer>[] adj;

    PracticeGraph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++)
            adj[i] = new LinkedList<>();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void BFS(int s) {
        boolean[] visited = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            s = queue.poll();
            System.out.print(s + " ");

            for (int n : adj[s]) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
}

class PraticeMain {
    public static void main(String[] args) {
        PracticeGraph g = new PracticeGraph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        g.BFS(2); /* 주어진 노드를 시작 노드로 BFS 탐색 */
    }
}


