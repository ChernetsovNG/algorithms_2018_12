package ru.nchernetsov;

import ru.nchernetsov.graph.Edge;
import ru.nchernetsov.graph.Vertex;
import ru.nchernetsov.graph.WeightedUndirectedGraph;
import ru.nchernetsov.heap.Heap;
import ru.nchernetsov.heap.MinHeap;

import java.util.Arrays;

import static ru.nchernetsov.heap.MinHeap.buildMinHeap;

/**
 * Алгоритм Краскала поиска минимального остовного дерева во взвешенном связном неориентированном графе
 */
public class KraskalAlg {

    /**
     * Алгоритм Краскала поиска минимального остовного дерева во взвешенном связном неориентированном графе
     *
     * @param graph взвешенный связный неориентированный граф
     * @return массив рёбер, составляющих минимальное остовное дерево графа
     */
    public static Edge[] kraskalMinimalSpanningTree(WeightedUndirectedGraph graph) {
        int edgesCount = graph.getEdgesCount();
        Edge[] originalEdges = graph.getEdges();
        int vertexCount = graph.getVertexCount();
        Vertex[] originalVertices = graph.getVertices();

        // копируем рёбра и вершины графа в рабочий массив
        Edge[] edges = Arrays.copyOf(originalEdges, edgesCount);
        Vertex[] vertices = Arrays.copyOf(originalVertices, vertexCount);

        /*
        1. Множество рёбер H искомого остовного дерева полагаем пустым
         */
        MyArrayList<Edge> H = new MyArrayList<>(edgesCount);

        /*
        2. Формируем множество Vs = {{v1},...,{vn}}, элементами которого являются множества
        вершин, соответствующих компонентам исходного остовного леса. Каждая такая компонента
        состоит из единственной вершины
         */
        MyArrayList<MyArrayList<Vertex>> Vs = new MyArrayList<>(vertexCount);
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            Vs.add(new MyArrayList<>());
            Vs.get(vertexIndex).add(vertices[vertexIndex]);
        }

        /*
        3. Сортируем множество рёбер E исходного графа по возрастанию весов и формируем очередь Q,
        элементами которой являются рёбра графа G
         */
        Heap<Edge> Q = buildMinHeap(edges);

        /*
        4. Если множество Vs содержит более одного элемента (т.е. остовный лес состоит из
        нескольких компонент) и очередь Q не пуста, переходим на шаг 5, иначе - прекращаем работу
         */
        while (Vs.size() > 1 && Q.size() > 0) {
            /*
            5. Извлекаем из очереди Q ребро e. Если концы ребра e принадлежат различным
            множествам вершин Vi и Vj из Vs, то переходим на шаг 6, иначе отбрасываем извлечённое
            ребро и возвращаемся на шаг 4
             */
            Edge e = Q.remove();
            int v1 = e.getV1();
            int v2 = e.getV2();
            int v1componentIndex = findVertexComponentIndex(v1, Vs);
            int v2componentIndex = findVertexComponentIndex(v2, Vs);
            if (v1componentIndex != v2componentIndex) {
                /*
                6. Объединяем множества вершин Vi и Vj (W = Vi + Vj), удаляем множества Vi и Vj
                из множества Vs и добавляем в Vs множество W. Добавляем ребро e в множество H.
                Возвращаемся на шаг 4
                 */
                int maxComponentIndex = Utils.max(v1componentIndex, v2componentIndex);
                int minComponentIndex = Utils.min(v1componentIndex, v2componentIndex);
                MyArrayList<Vertex> Vi = Vs.remove(maxComponentIndex);
                MyArrayList<Vertex> Vj = Vs.remove(minComponentIndex);
                MyArrayList<Vertex> W = new MyArrayList<>(Vi.size() + Vj.size());
                W.addAll(Vi);
                W.addAll(Vj);
                Vs.add(W);

                H.add(e);
            }
        }

        return H.toArray(new Edge[0]);
    }

    /**
     * Найти индекс компонента остовного леса, в который входит заданная вершина
     *
     * @param vertexIndex индекс вершины
     * @param Vs          множество компонент основного леса
     * @return индекс компонента основного леса, в который входит заданная вершина,
     * или -1, если такая вершина не найдена
     */
    private static int findVertexComponentIndex(int vertexIndex, MyArrayList<MyArrayList<Vertex>> Vs) {
        int componentsCount = Vs.size();
        for (int componentIndex = 0; componentIndex < componentsCount; componentIndex++) {
            MyArrayList<Vertex> component = Vs.get(componentIndex);
            for (Vertex vertex : component) {
                if (vertex.getIndex() == vertexIndex) {
                    return componentIndex;
                }
            }
        }
        return -1;
    }
}
