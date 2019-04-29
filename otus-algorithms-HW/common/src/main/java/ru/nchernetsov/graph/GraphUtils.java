package ru.nchernetsov.graph;

import ru.nchernetsov.MyArrayList;
import ru.nchernetsov.tuples.Pair;

public final class GraphUtils {

    public static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Преобразовать список векторов смежности графа в матрицу смежности
     *
     * @param vertexCount    количество вершин графа
     * @param adjVectorsList список векторов смежности
     * @return матрица смежности
     */
    public static int[][] convertAdjVectorsToAdjMatrix(int vertexCount, MyArrayList<MyArrayList<Integer>> adjVectorsList) {
        int[][] adjMatrix = new int[vertexCount][vertexCount];

        // диагональные элементы - нули
        for (int i = 0; i < vertexCount; i++) {
            adjMatrix[i][i] = 0;
        }

        for (int vertexIndex = 0; vertexIndex < adjVectorsList.size(); vertexIndex++) {
            MyArrayList<Integer> adjVertices = adjVectorsList.get(vertexIndex);
            for (Integer adjVertexIndex : adjVertices) {
                adjMatrix[vertexIndex][adjVertexIndex] = 1;
            }
        }

        return adjMatrix;
    }

    /**
     * Преобразовать список векторов смежности взвешенного графа в матрицу смежности
     *
     * @param vertexCount    количество вершин графа
     * @param adjVectorsList список векторов смежности
     * @return матрица смежности
     */
    public static int[][] convertAdjVectorsToWeightAdjMatrix(int vertexCount, MyArrayList<MyArrayList<Pair<Integer, Integer>>> adjVectorsList) {
        int[][] adjMatrix = new int[vertexCount][vertexCount];

        // вначале заполняем все элементы бесконечными расстояниями
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjMatrix[i][j] = INFINITY;
            }
        }

        // диагональные элементы - нули
        for (int i = 0; i < vertexCount; i++) {
            adjMatrix[i][i] = 0;
        }

        for (int vertexIndex = 0; vertexIndex < adjVectorsList.size(); vertexIndex++) {
            MyArrayList<Pair<Integer, Integer>> adjVertices = adjVectorsList.get(vertexIndex);
            for (Pair<Integer, Integer> adjVertexIndexWeight : adjVertices) {
                Integer adjVertexIndex = adjVertexIndexWeight.getFirst();
                Integer adjVertexPathWeight = adjVertexIndexWeight.getSecond();
                adjMatrix[vertexIndex][adjVertexIndex] = adjVertexPathWeight;
            }
        }

        return adjMatrix;
    }

    public static double[][] convertAdjVectorsToDoubleWeightAdjMatrix(int vertexCount, MyArrayList<MyArrayList<Pair<Integer, Double>>> adjVectorsList) {
        double[][] adjMatrix = new double[vertexCount][vertexCount];

        // вначале заполняем все элементы бесконечными расстояниями
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjMatrix[i][j] = INFINITY;
            }
        }

        // диагональные элементы - нули
        for (int i = 0; i < vertexCount; i++) {
            adjMatrix[i][i] = 0.0;
        }

        for (int vertexIndex = 0; vertexIndex < adjVectorsList.size(); vertexIndex++) {
            MyArrayList<Pair<Integer, Double>> adjVertices = adjVectorsList.get(vertexIndex);
            for (Pair<Integer, Double> adjVertexIndexWeight : adjVertices) {
                Integer adjVertexIndex = adjVertexIndexWeight.getFirst();
                Double adjVertexPathWeight = adjVertexIndexWeight.getSecond();
                adjMatrix[vertexIndex][adjVertexIndex] = adjVertexPathWeight;
            }
        }

        return adjMatrix;
    }

    /**
     * Вычисление расстояния между двумя точками на плоскости
     *
     * @param vertex1 точка 1
     * @param vertex2 точка 2
     * @return расстояние
     */
    public static double distance(VertexPoint2D vertex1, VertexPoint2D vertex2) {
        double x1 = vertex1.getX();
        double y1 = vertex1.getY();
        double x2 = vertex2.getX();
        double y2 = vertex2.getY();

        double x2x1diff = x2 - x1;
        double y2y1diff = y2 - y1;

        return Math.sqrt(x2x1diff * x2x1diff + y2y1diff * y2y1diff);
    }
}
