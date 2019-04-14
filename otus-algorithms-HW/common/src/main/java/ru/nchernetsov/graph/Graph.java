package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;

import java.util.Comparator;

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

    public Graph(int maxVerticesCount) {
        maxVertexCount = maxVerticesCount;
        vertices = new Vertex[maxVertexCount];
        adjVectorsList = new MyArrayList<>(maxVertexCount);
        for (int i = 0; i < maxVerticesCount; i++) {
            adjVectorsList.add(new MyArrayList<>(10));
        }
        vertexCount = 0;
    }

    // конструктор для графа, заданного вектором смежности
    public Graph(int[][] A) {
        this(A.length);
        int vertexCountA = A.length;
        for (int i = 0; i < vertexCountA; i++) {
            addVertex(i);
            for (int j = 0; j < A[i].length; j++) {
                int adjVertexIndex = A[i][j];
                addEdge(i, adjVertexIndex);
            }
        }
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

    public MyArrayList<MyArrayList<Integer>> getAdjVectorsList() {
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
}
