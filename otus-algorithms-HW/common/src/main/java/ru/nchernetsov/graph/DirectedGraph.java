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

    @Override
    public void addEdge(int from, int to) {
        // добавляем к вектору смежности первой вершины вторую вершину
        MyArrayList<Integer> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(to);
        // сортируем смежные вершины по возрастанию
        fromVertexAdjVector.sort(Comparator.naturalOrder());
    }
}
