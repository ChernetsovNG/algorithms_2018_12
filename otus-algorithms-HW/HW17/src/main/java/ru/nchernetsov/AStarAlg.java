package ru.nchernetsov;

import ru.nchernetsov.graph.VertexPoint2D;
import ru.nchernetsov.graph.WeightedDirectedGraph2D;
import ru.nchernetsov.heap.MinHeap;
import ru.nchernetsov.tuples.Pair;

/**
 * Алгоритм A* поиска маршрута с наименьшей стоимостью между двумя вершинами в графе
 */
public class AStarAlg {

    /**
     * Алгоритм A* поиска кратчайшего пути во взвешенном ориентированном графе
     *
     * @param graph      взвешенный ориентированный граф, образованный вершинами в виде точек на плоскости
     * @param vertexFrom индекс начальной вершины
     * @param vertexTo   индекс конечной вершины
     * @return массив вершин, составляющих кратчайший путь из начальной вершины в конечную
     */
    public static Path aStarAlg(WeightedDirectedGraph2D graph, int vertexFrom, int vertexTo) {
        int edgesCount = graph.getEdgesCount();
        VertexPoint2D[] vertices = graph.getVertices();
        MyArrayList<MyArrayList<Pair<Integer, Double>>> adjVectorsList = graph.getAdjVectorsList();

        VertexPoint2D start = vertices[vertexFrom];
        VertexPoint2D goal = vertices[vertexTo];

        // Множество частных решений
        MinHeap<Path> open = new MinHeap<>(edgesCount);

        open.add(new Path(start, goal));
        while (open.isNotEmpty()) {
            Path p = open.remove();
            VertexPoint2D x = p.lastVertex();
            if (x.isVisited()) {
                continue;
            }
            if (x.getIndex() == vertexTo) {
                return p;
            }
            x.setVisited(true);
            // добавляем смежные вершины
            MyArrayList<Pair<Integer, Double>> adjVector = adjVectorsList.get(x.getIndex());
            for (Pair<Integer, Double> adjVertexAndEdgeWeight : adjVector) {
                Integer adjVertexIndex = adjVertexAndEdgeWeight.getFirst();
                VertexPoint2D adjVertex = vertices[adjVertexIndex];
                Path newPath = new Path(p);
                newPath.addVertex(adjVertex);
                open.add(newPath);
            }
        }

        // if path not found => return failure empty path
        return new Path(vertices[vertexTo]);
    }
}
