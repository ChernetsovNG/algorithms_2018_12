package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;
import ru.nchernetsov.tuples.Pair;

import java.util.Comparator;

import static ru.nchernetsov.graph.GraphUtils.distance;

/**
 * Взвешенный ориентированный граф с вершинами в виде точек на плоскости
 */
public class WeightedDirectedGraph2D {

    /**
     * Вектора смежности == A[N][S_max], где S_max - максимальная степень вершины графа
     * Помимо номера вершины в векторе смежности хранится ещё вес ребра
     */
    private final MyArrayList<MyArrayList<Pair<Integer, Double>>> adjVectorsList;

    private final int maxVertexCount;

    private final int maxEdgesCount;

    /**
     * Массив вершин графа
     */
    private final VertexPoint2D[] vertices;

    /**
     * Массив рёбер графа
     */
    private final Edge[] edges;

    private int vertexCount;

    private int edgesCount;

    public WeightedDirectedGraph2D(int maxVerticesCount, int maxEdgesCount) {
        this.maxVertexCount = maxVerticesCount;
        this.maxEdgesCount = maxEdgesCount;
        vertices = new VertexPoint2D[maxVertexCount];
        edges = new Edge[maxEdgesCount];
        adjVectorsList = new MyArrayList<>(maxVertexCount);
        for (int i = 0; i < maxVerticesCount; i++) {
            adjVectorsList.add(new MyArrayList<>(10));
        }
        vertexCount = 0;
        edgesCount = 0;
    }

    public void addVertex(int index, double x, double y) {
        if (vertexCount >= maxVertexCount) {
            throw new IllegalArgumentException("Graph vertices array is full (maximum size = " + maxVertexCount + " reached)");
        }
        VertexPoint2D newVertex = new VertexPoint2D(index, x, y);
        vertices[index] = newVertex;
        vertexCount++;
    }

    public void addEdge(int from, int to) {
        if (edgesCount >= maxEdgesCount) {
            throw new IllegalArgumentException("Graph edges array is full (maximum size = " + maxEdgesCount + " reached)");
        }

        // добавляем к вектору смежности первой вершины вторую вершину
        VertexPoint2D vertexFrom = vertices[from];
        VertexPoint2D vertexTo = vertices[to];

        double edgeLength = distance(vertexFrom, vertexTo);

        MyArrayList<Pair<Integer, Double>> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(Pair.of(to, edgeLength));

        // сортируем смежные вершины по возрастанию индекса
        fromVertexAdjVector.sort(Comparator.comparing(Pair::getFirst));

        Edge newEdge = new Edge(from, to, edgeLength);
        edges[edgesCount++] = newEdge;
    }

    public MyArrayList<MyArrayList<Pair<Integer, Double>>> getAdjVectorsList() {
        return adjVectorsList;
    }

    public int getMaxVertexCount() {
        return maxVertexCount;
    }

    public VertexPoint2D[] getVertices() {
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
