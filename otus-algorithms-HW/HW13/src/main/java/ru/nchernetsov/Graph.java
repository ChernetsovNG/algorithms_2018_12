package ru.nchernetsov;

import java.util.Comparator;
import java.util.function.Consumer;

public class Graph {

    /**
     * Вектора смежности == A[N][S_max], где S_max - максимальная степень вершины графа
     */
    private final MyArrayList<MyArrayList<Integer>> adjVectorsList;

    private final int maxVertexCount;

    /**
     * Массив вершин графа
     */
    private final Vertex[] vertices;

    private int vertexCount;

    public Graph(int maxSize) {
        maxVertexCount = maxSize;
        vertices = new Vertex[maxVertexCount];
        adjVectorsList = new MyArrayList<>(maxVertexCount);
        for (int i = 0; i < maxSize; i++) {
            adjVectorsList.add(new MyArrayList<>(10));
        }
        vertexCount = 0;
    }

    public void addVertex(int index) {
        if (vertexCount >= maxVertexCount) {
            throw new IllegalArgumentException("Graph is full (maximum size = " + maxVertexCount + " reached)");
        }
        Vertex newVertex = new Vertex(index);
        vertices[index] = newVertex;
        vertexCount++;
    }

    public void addEdge(int from, int to) {
        // добавляем к вектору смежности первой вершины вторую вершину
        MyArrayList<Integer> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(to);
        // сортируем смежные вершины по возрастанию
        fromVertexAdjVector.sort(Comparator.naturalOrder());
    }

    /**
     * Алгоритм обхода графа (поиска) в глубину
     */
    public void dfs(int startVertexIndex, Consumer<Vertex> action) {
        Stack<Integer> stack = new Stack<>(vertexCount);

        stack.push(startVertexIndex);
        while (!stack.isEmpty()) {
            // Берём верхнюю вершину
            Integer topVertexIndex = stack.pop();
            Vertex topVertex = vertices[topVertexIndex];
            if (!topVertex.isVisited()) {
                action.accept(topVertex);
                topVertex.setVisited(true);
                // Кладём в стек все смежные непосещённые вершины
                MyArrayList<Integer> topVertexAdjVector = adjVectorsList.get(topVertexIndex);
                for (Integer adjVertexIndex : topVertexAdjVector) {
                    Vertex adjVertex = vertices[adjVertexIndex];
                    if (!adjVertex.isVisited()) {
                        stack.push(adjVertexIndex);
                    }
                }
            }
        }

        // после обхода проставляем в вершинах isVisited = false
        for (int i = 0; i < vertexCount; i++) {
            vertices[i].setVisited(false);
        }
    }

    /**
     * Получить из заданного графа граф с инвертированными рёбрами
     *
     * @return граф с инвертированными рёбрами
     */
    public Graph getInvertedGraph() {
        Graph invertedGraph = new Graph(maxVertexCount);
        // копируем массив вершин из данного графа в инвертированный
        for (int i = 0; i < vertices.length; i++) {
            invertedGraph.addVertex(i);
        }
        // создаём список векторов смежности инвертированного графа
        for (int i = 0; i < vertexCount; i++) {
            MyArrayList<Integer> adjVectorVertexI = adjVectorsList.get(i);
            for (Integer toVertex : adjVectorVertexI) {
                invertedGraph.addEdge(toVertex, i);  // добавляем обратное ребро
            }
        }
        return invertedGraph;
    }

    /*

    Пусть G — исходный граф, H — инвертированный граф.
    В массиве ord будем хранить номера вершин в порядке окончания обработки
    поиском в глубину в графе G. В результате получаем массив component,
    который каждой вершине сопоставляет номер её компоненты.

    function dfs1(v):
       color[v] = 1
       for (v, u) in E
           if not visited[u]
               dfs1(G[v][u])
       Добавляем вершину v в конец списка ord

   function dfs2(v):
       component[v] = col
       for (v, u) in E
           if (вершина u еще не находится ни в какой компоненте)
               dfs2(H[v][u])

   function main():
       считываем исходные данные, формируем массивы G и H
       for u in V
           if not visited[u]
               dfs1(u)
       col = 1
       for (по всем вершинам u списка ord[] в обратном порядке)
           if (вершина u не находится ни в какой компоненте)
               dfs2(u)
               col++

     */

    public MyArrayList<MyArrayList<Integer>> getAdjVectorsList() {
        return adjVectorsList;
    }
}
