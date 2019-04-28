package ru.nchernetsov;

import ru.nchernetsov.sort.Utils;

import java.util.Arrays;
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

    /**
     * Алгоритм обхода графа (поиска) в глубину
     */
    public void dfs(int startVertexIndex, Consumer<Vertex> action, boolean resetIsVisited) {
        Stack<Integer> stack = new Stack<>(vertexCount);
        stack.push(startVertexIndex);
        while (!stack.isEmpty()) {
            Integer topVertexIndex = stack.pop();
            Vertex topVertex = vertices[topVertexIndex];
            if (!topVertex.isVisited()) {
                if (action != null) {
                    action.accept(topVertex);
                }
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
        if (resetIsVisited) {
            for (int i = 0; i < vertexCount; i++) {
                vertices[i].setVisited(false);
            }
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

    /**
     * Алгоритм Косарайю поиска сильно связных компонент графа
     *
     * @return список списков из индексов вершин, образующих сильно связанные компоненты графа
     */
    public MyArrayList<MyArrayList<Integer>> getStronglyConnectedComponents() {
        int[] stronglyConnectedComponentsArray = getStronglyConnectedComponentsArray();

        int componentsCount = Utils.max(stronglyConnectedComponentsArray);

        MyArrayList<MyArrayList<Integer>> result = new MyArrayList<>(componentsCount);
        for (int i = 0; i < componentsCount; i++) {
            result.add(new MyArrayList<>());
        }

        for (int vertexIndex = 0; vertexIndex < stronglyConnectedComponentsArray.length; vertexIndex++) {
            int componentNum = stronglyConnectedComponentsArray[vertexIndex];
            // добавляем к соответствующему компоненту индекс вершины
            result.get(componentNum - 1).add(vertexIndex);
        }

        return result;
    }

    /**
     * Алгоритм Косарайю поиска сильно связных компонент графа
     *
     * @return массив component, который каждой вершине сопоставляет номер её компонента связности
     * Компоненты связности нумеруются от единицы
     */
    private int[] getStronglyConnectedComponentsArray() {
        // Пусть G — исходный граф (this), H — инвертированный граф
        Graph h = getInvertedGraph();

        // вычисляем реверсивный порядок обхода в инвертированном графе
        DepthFirstOrder order = new DepthFirstOrder(h);

        // инициализируем массив результатов
        int[] components = new int[vertexCount];
        Arrays.fill(components, -1);

        // индекс компонента связности
        final int[] componentNum = {1};

        // по всем вершинам в реверсивном порядке обхода
        for (int vertexIndex : order.reversePost()) {
            // если вершина еще не находится ни в какой компоненте связности
            if (components[vertexIndex] == -1) {
                dfs(vertexIndex, v -> components[v.getIndex()] = componentNum[0], false);
                componentNum[0] += 1;
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            vertices[i].setVisited(false);
        }

        return components;
    }

    public MyArrayList<MyArrayList<Integer>> getAdjVectorsList() {
        return adjVectorsList;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
