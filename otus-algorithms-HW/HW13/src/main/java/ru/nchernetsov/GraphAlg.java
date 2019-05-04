package ru.nchernetsov;

import ru.nchernetsov.graph.DirectedGraph;
import ru.nchernetsov.graph.Graph;
import ru.nchernetsov.graph.Vertex;
import ru.nchernetsov.sort.Utils;

import java.util.Arrays;
import java.util.function.Consumer;

public class GraphAlg {

    /**
     * Алгоритм обхода графа (поиска) в глубину
     */
    public static void dfs(Graph graph, int startVertexIndex, Consumer<Vertex> action, boolean resetIsVisited) {
        int vertexCount = graph.getVertexCount();
        Vertex[] vertices = graph.getVertices();
        MyArrayList<MyArrayList<Integer>> adjVectorsList = graph.getAdjVectorsList();

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
    public static DirectedGraph getInvertedGraph(DirectedGraph graph) {
        int maxVertexCount = graph.getMaxVertexCount();
        int vertexCount = graph.getVertexCount();
        Vertex[] vertices = graph.getVertices();
        MyArrayList<MyArrayList<Integer>> adjVectorsList = graph.getAdjVectorsList();

        DirectedGraph invertedGraph = new DirectedGraph(maxVertexCount);
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
    public static MyArrayList<MyArrayList<Integer>> getStronglyConnectedComponents(DirectedGraph graph) {
        int[] stronglyConnectedComponentsArray = getStronglyConnectedComponentsArray(graph);

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
    private static int[] getStronglyConnectedComponentsArray(DirectedGraph graph) {
        Vertex[] vertices = graph.getVertices();
        int vertexCount = graph.getVertexCount();

        // Пусть G — исходный граф (this), H — инвертированный граф
        Graph h = getInvertedGraph(graph);

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
                dfs(graph, vertexIndex, v -> components[v.getIndex()] = componentNum[0], false);
                componentNum[0] += 1;
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            vertices[i].setVisited(false);
        }

        return components;
    }
}
