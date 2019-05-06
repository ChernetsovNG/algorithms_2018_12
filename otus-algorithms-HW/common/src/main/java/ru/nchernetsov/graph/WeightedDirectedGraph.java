package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;
import ru.nchernetsov.tuples.Pair;

import java.util.Comparator;

/**
 * Взвешенный ориентированный граф
 */
public class WeightedDirectedGraph {

    /**
     * Вектора смежности == A[N][S_max], где S_max - максимальная степень вершины графа
     * Помимо номера вершины в векторе смежности хранится ещё вес ребра
     */
    private final MyArrayList<MyArrayList<Pair<Integer, Integer>>> adjVectorsList;

    private final int maxVertexCount;

    private final int maxEdgesCount;

    /**
     * Массив вершин графа
     */
    private final Vertex[] vertices;

    /**
     * Массив рёбер графа
     */
    private final Edge[] edges;

    private int vertexCount;

    private int edgesCount;

    public WeightedDirectedGraph(int maxVerticesCount, int maxEdgesCount) {
        this.maxVertexCount = maxVerticesCount;
        this.maxEdgesCount = maxEdgesCount;
        vertices = new Vertex[maxVertexCount];
        edges = new Edge[maxEdgesCount];
        adjVectorsList = new MyArrayList<>(maxVertexCount);
        for (int i = 0; i < maxVerticesCount; i++) {
            adjVectorsList.add(new MyArrayList<>(10));
        }
        vertexCount = 0;
        edgesCount = 0;
    }

    public void addVertex(int index) {
        if (vertexCount >= maxVertexCount) {
            throw new IllegalArgumentException("Graph vertices array is full (maximum size = " + maxVertexCount + " reached)");
        }
        Vertex newVertex = new Vertex(index);
        vertices[index] = newVertex;
        vertexCount++;
    }

    public void addEdge(int from, int to, int weight) {
        if (edgesCount >= maxEdgesCount) {
            throw new IllegalArgumentException("Graph edges array is full (maximum size = " + maxEdgesCount + " reached)");
        }

        // добавляем к вектору смежности первой вершины вторую вершину
        MyArrayList<Pair<Integer, Integer>> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(Pair.of(to, weight));

        // сортируем смежные вершины по возрастанию индекса
        fromVertexAdjVector.sort(Comparator.comparing(Pair::getFirst));

        Edge newEdge = new Edge(from, to, weight);
        edges[edgesCount++] = newEdge;
    }

    public MyArrayList<MyArrayList<Pair<Integer, Integer>>> getAdjVectorsList() {
        return adjVectorsList;
    }

    public int getMaxVertexCount() {
        return maxVertexCount;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getEdgesCount() {
        return edgesCount;
    }
}
