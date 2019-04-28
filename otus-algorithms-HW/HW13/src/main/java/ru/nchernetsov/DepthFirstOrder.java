package ru.nchernetsov;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Вспомогательный класс для обхода графа в глубину с сохранением различных порядков обхода
 */
public class DepthFirstOrder {

    private boolean[] marked;

    /**
     * Вершины в прямом порядке (вершины заносятся в очередь перед рекурсивными вызовами)
     */
    private Queue<Integer> pre;

    /**
     * Вершины в обратном порядке (вершины заносятся в очередь после рекурсивных вызовов)
     */
    private Queue<Integer> post;

    /**
     * Вершины в реверсивном порядке (вершины заносятся в стек после рекурсивных вызовов)
     */
    private Stack<Integer> reversePost;

    private final MyArrayList<MyArrayList<Integer>> adjVectorsList;

    public DepthFirstOrder(Graph graph) {
        pre = new ArrayDeque<>();
        post = new ArrayDeque<>();
        reversePost = new Stack<>();
        marked = new boolean[graph.getVertexCount()];
        adjVectorsList = graph.getAdjVectorsList();
        for (int v = 0; v < graph.getVertexCount(); v++) {
            if (!marked[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int vertexIndex) {
        pre.offer(vertexIndex);
        marked[vertexIndex] = true;
        MyArrayList<Integer> vertexAdjVector = adjVectorsList.get(vertexIndex);
        for (Integer adjVertexIndex : vertexAdjVector) {
            if (!marked[adjVertexIndex]) {
                dfs(adjVertexIndex);
            }
        }
        post.offer(vertexIndex);
        reversePost.push(vertexIndex);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
