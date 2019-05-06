package ru.nchernetsov;

import ru.nchernetsov.graph.Edge;
import ru.nchernetsov.graph.Vertex;
import ru.nchernetsov.graph.WeightedDirectedGraph;
import ru.nchernetsov.tuples.Pair;

import java.util.Arrays;

import static ru.nchernetsov.graph.GraphUtils.INFINITY;
import static ru.nchernetsov.graph.GraphUtils.convertAdjVectorsToWeightAdjMatrix;

/**
 * Алгоритм Дейкстры
 */
public class DijkstraAlg {

    /**
     * Алгоритм Дейкстры поиска кратчайшего пути во взвешенном ориентированном графе
     *
     * @param graph      взвешенный ориентированный граф
     * @param vertexFrom начальная вершина
     * @param vertexTo   конечная вершина
     * @return массив рёбер, составляющих кратчайший путь из начальной вершины в конечную
     */
    public static Edge[] dijkstraAlg(WeightedDirectedGraph graph, int vertexFrom, int vertexTo) {
        int vertexCount = graph.getVertexCount();
        Vertex[] vertices = graph.getVertices();
        MyArrayList<MyArrayList<Pair<Integer, Integer>>> adjVectorsList = graph.getAdjVectorsList();
        // преобразуем представление графа в матрицу смежности
        int[][] adjMatrix = convertAdjVectorsToWeightAdjMatrix(vertexCount, adjVectorsList);

        DistanceParent[] sPath = getShortestPathToAllVertices(vertexFrom, vertices, adjMatrix);

        // После того, как кратчайшие расстояния до всех вершин в графе вычислены, восстанавливаем
        // с конца кратчайший путь между заданными вершинами
        return restoreShortestPath(vertexFrom, vertexTo, sPath, adjMatrix);
    }

    /**
     * Для всех вершин графа вычислить кратчайший путь до них из заданной вершины
     *
     * @param vertexFrom    заданная вершина графа
     * @param graphVertices массив вершин графа
     * @param adjMatrix     матрица смежности графа
     * @return массив, в котором для каждой вершины графа вычислено расстояние до неё из заданной вершины,
     * а также непосредственный родитель на кратчайшем пути из заданной вершины графа до неё
     */
    private static DistanceParent[] getShortestPathToAllVertices(int vertexFrom, Vertex[] graphVertices, int[][] adjMatrix) {
        int vertexCount = graphVertices.length;

        // копируем вершины графа в рабочий массив
        Vertex[] vertices = Arrays.copyOf(graphVertices, vertexCount);

        // массив, содержащий кратчайшие расстояния от заданной вершины до всех остальных
        DistanceParent[] sPath = new DistanceParent[vertexCount];

        // перемещение строки расстояний из adjMat в sPath
        for (int j = 0; j < vertexCount; j++) {
            int tempDist = adjMatrix[vertexFrom][j];
            sPath[j] = new DistanceParent(tempDist, vertexFrom);
        }

        // включение в дерево начальной вершины
        int nTree = 1;
        vertices[vertexFrom].setVisited(true);

        int currentVert;
        // Минимальное расстояние от startTree до currentVer
        int startToCurrent;
        while (nTree < vertexCount) {  // пока все вершины не окажутся в дереве
            int indexMin = getMinDistVertex(sPath, vertices);  // получение минимума из sPath
            int minDist = sPath[indexMin].getDistance();
            // Если все расстояния бесконечны, или все соседние вершины уже находятся в дереве
            if (minDist == INFINITY) {
                System.out.println("There are unreachable vertices");
                break;  // построение sPath завершено
            } else {
                currentVert = indexMin;  // возврат currentVert к ближайшей вершине
                startToCurrent = sPath[indexMin].getDistance();
            }
            // Включение текущей вершины в дерево
            vertices[currentVert].setVisited(true);
            nTree++;
            adjustSPath(sPath, vertices, adjMatrix, currentVert, startToCurrent);  // обновление массива sPath
        }

        return sPath;
    }

    /**
     * Поиск в sPath элемента с наименьшим расстоянием
     *
     * @return индекс элемента с наименьшим расстоянием
     */
    private static int getMinDistVertex(DistanceParent[] sPath, Vertex[] vertices) {
        int vertexCount = vertices.length;
        int minDist = INFINITY;
        int indexMin = 0;
        for (int j = 0; j < vertexCount; j++) {
            // для каждой вершины, если она не включена в дерево, и её расстояние меньше старого минимума
            if (!vertices[j].isVisited() && sPath[j].getDistance() < minDist) {
                minDist = sPath[j].getDistance();
                indexMin = j;
            }
        }
        return indexMin;
    }

    /**
     * Обновление данных в массиве кратчайших путей sPath
     */
    private static void adjustSPath(DistanceParent[] sPath, Vertex[] vertices, int[][] adjMatrix,
                                    int currentVert, int startToCurrent) {
        int vertexCount = vertices.length;
        for (int j = 0; j < vertexCount; j++) {
            if (vertices[j].isVisited()) {  // Если вершина уже включена в дерево, то она пропускается
                continue;
            }
            int currentToFringe = adjMatrix[currentVert][j];
            int startToFringe;
            if (startToCurrent <= INFINITY - currentToFringe) {
                startToFringe = startToCurrent + currentToFringe;
            } else {
                startToFringe = INFINITY;
            }
            int sPathDist = sPath[j].getDistance();
            if (startToFringe < sPathDist) {
                sPath[j].setParentVertex(currentVert);
                sPath[j].setDistance(startToFringe);
            }
        }
    }

    /**
     * По вычисленному массиву кратчайших расстояний восстанавливаем путь между заданными вершинами
     *
     * @param vertexFrom начальная вершина
     * @param vertexTo   конечная вершина
     * @param sPath      массив кратчайших расстояний между начальной веришной и остальными вершинами графа
     * @param adjMatrix  матрица смежности графа
     * @return массив рёбер, составляющих кратчайший путь между заданными вершинами графа
     */
    private static Edge[] restoreShortestPath(int vertexFrom, int vertexTo, DistanceParent[] sPath, int[][] adjMatrix) {
        MyArrayList<Edge> result = new MyArrayList<>();

        int vertex1 = vertexTo;
        int vertex2 = vertexTo;
        while (vertex1 != vertexFrom) {
            DistanceParent distanceParentVertex2 = sPath[vertex2];
            int distanceToVertex2 = distanceParentVertex2.getDistance();
            if (distanceToVertex2 == INFINITY) {  // пути от вершины vertexFrom до vertexTo нет
                return new Edge[0];
            }
            vertex1 = distanceParentVertex2.getParentVertex();
            int distFromVertex1to2 = adjMatrix[vertex1][vertex2];
            result.add(new Edge(vertex1, vertex2, distFromVertex1to2));
            vertex2 = vertex1;
        }
        int pathEdgeCount = result.size();

        Edge[] resultArray = new Edge[pathEdgeCount];
        for (int i = pathEdgeCount - 1; i >= 0; i--) {
            Edge edge = result.get(i);
            resultArray[pathEdgeCount - 1 - i] = edge;
        }
        return resultArray;
    }
}
