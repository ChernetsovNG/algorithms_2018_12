package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;

import java.util.Comparator;

/**
 * Ненаправленный граф
 */
public class UndirectedGraph extends Graph {

    public UndirectedGraph(int maxVerticesCount) {
        super(maxVerticesCount);
    }

    @Override
    public void addEdge(int from, int to) {
        // добавляем к вектору смежности первой вершины вторую вершину
        MyArrayList<Integer> fromVertexAdjVector = adjVectorsList.get(from);
        fromVertexAdjVector.add(to);

        // добавляем к вектору смежности второй вершины первую вершину
        MyArrayList<Integer> toVertexAdjVector = adjVectorsList.get(to);
        toVertexAdjVector.add(from);

        // сортируем смежные вершины по возрастанию
        fromVertexAdjVector.sort(Comparator.naturalOrder());
        toVertexAdjVector.sort(Comparator.naturalOrder());
    }
}
