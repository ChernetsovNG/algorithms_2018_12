package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;

import java.util.Comparator;

/**
 * Направленный граф
 */
public class DirectedGraph extends Graph {

    public DirectedGraph(int maxVerticesCount) {
        super(maxVerticesCount);
    }

    // конструктор для графа, заданного вектором смежности
    public DirectedGraph(int[][] A) {
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

    @Override
    public void addEdge(int from, int to) {
        // добавляем к вектору смежности первой вершины вторую вершину
        MyArrayList<Integer> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(to);
        // сортируем смежные вершины по возрастанию
        fromVertexAdjVector.sort(Comparator.naturalOrder());
    }
}
