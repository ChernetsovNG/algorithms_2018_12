package ru.nchernetsov;

import ru.nchernetsov.graph.DirectedGraph;
import ru.nchernetsov.graph.Graph;
import ru.nchernetsov.graph.Vertex;
import ru.nchernetsov.sort.Utils;

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
    public static int[] getStronglyConnectedComponentsArray(DirectedGraph graph) {
        int vertexCount = graph.getVertexCount();
        Vertex[] vertices = graph.getVertices();

        // Пусть G — исходный граф (this), H — инвертированный граф
        DirectedGraph h = getInvertedGraph(graph);

        // В массиве ord будем хранить номера вершин в порядке окончания обработки
        // поиском в глубину в графе G
        int[] ord = new int[vertexCount];

        final int[] j = {0};
        for (int i = 0; i < vertexCount; i++) {
            Vertex vertex = vertices[i];
            if (!vertex.isVisited()) {
                dfs(graph, i, v -> {
                    ord[j[0]] = v.getIndex();
                    j[0] = j[0] + 1;
                }, false);
            }
        }

        // инициализируем массив результатов
        int[] components = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            components[i] = -1;
        }

        // индекс компонента связности
        final int[] componentNum = {1};

        // по всем вершинам из списка ord[] в обратном порядке
        for (int i = ord.length - 1; i >= 0; i--) {
            int vertexIndex = ord[i];
            // если вершина еще не находится ни в какой компоненте связности
            if (components[vertexIndex] == -1) {
                dfs(h, vertexIndex, v -> components[v.getIndex()] = componentNum[0], false);
                componentNum[0] = componentNum[0] + 1;
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            vertices[i].setVisited(false);
        }

        return components;
    }
}
