package ru.nchernetsov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GraphTest {

    @Test
    public void graphTest1() {
        Graph graph = createTestGraph();

        MyArrayList<MyArrayList<Integer>> adjVectorsList = graph.getAdjVectorsList();

        MyArrayList<Integer> adjVectorVertex0 = adjVectorsList.get(0);
        MyArrayList<Integer> adjVectorVertex1 = adjVectorsList.get(1);
        MyArrayList<Integer> adjVectorVertex2 = adjVectorsList.get(2);
        MyArrayList<Integer> adjVectorVertex3 = adjVectorsList.get(3);
        MyArrayList<Integer> adjVectorVertex4 = adjVectorsList.get(4);

        assertThat(adjVectorVertex0).hasSize(2);
        assertThat(adjVectorVertex1).hasSize(3);
        assertThat(adjVectorVertex2).hasSize(1);
        assertThat(adjVectorVertex3).hasSize(1);
        assertThat(adjVectorVertex4).isEmpty();

        assertThat(adjVectorVertex0).containsExactly(1, 2);
        assertThat(adjVectorVertex1).containsExactly(2, 3, 4);
        assertThat(adjVectorVertex2).containsExactly(3);
        assertThat(adjVectorVertex3).containsExactly(4);

        List<Integer> traversalOrder = new ArrayList<>();
        graph.dfs(0, vertex -> traversalOrder.add(vertex.getIndex()), true);

        assertThat(traversalOrder).containsExactly(0, 2, 3, 4, 1);
    }

    @Test
    public void getInvertedGraphTest1() {
        Graph graph = createTestGraph();

        Graph invertedGraph = graph.getInvertedGraph();

        // проверяем вектора смежности инвертированного графа
        MyArrayList<MyArrayList<Integer>> adjVectorsList = invertedGraph.getAdjVectorsList();

        MyArrayList<Integer> adjVectorVertex0 = adjVectorsList.get(0);
        MyArrayList<Integer> adjVectorVertex1 = adjVectorsList.get(1);
        MyArrayList<Integer> adjVectorVertex2 = adjVectorsList.get(2);
        MyArrayList<Integer> adjVectorVertex3 = adjVectorsList.get(3);
        MyArrayList<Integer> adjVectorVertex4 = adjVectorsList.get(4);

        assertThat(adjVectorVertex0).isEmpty();
        assertThat(adjVectorVertex1).hasSize(1);
        assertThat(adjVectorVertex2).hasSize(2);
        assertThat(adjVectorVertex3).hasSize(2);
        assertThat(adjVectorVertex4).hasSize(2);

        assertThat(adjVectorVertex1).containsExactly(0);
        assertThat(adjVectorVertex2).containsExactly(0, 1);
        assertThat(adjVectorVertex3).containsExactly(1, 2);
        assertThat(adjVectorVertex4).containsExactly(1, 3);
    }

    @Test
    public void getStronglyConnectedComponentsTest1() {
        Graph graph = new Graph(11);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(6, 5);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
        graph.addEdge(9, 6);
        graph.addEdge(10, 9);

        MyArrayList<MyArrayList<Integer>> stronglyConnectedComponents = graph.getStronglyConnectedComponents();

        assertThat(stronglyConnectedComponents).hasSize(4);

        MyArrayList<Integer> component1 = stronglyConnectedComponents.get(0);
        MyArrayList<Integer> component2 = stronglyConnectedComponents.get(1);
        MyArrayList<Integer> component3 = stronglyConnectedComponents.get(2);
        MyArrayList<Integer> component4 = stronglyConnectedComponents.get(3);

        assertThat(component1).hasSize(3);
        assertThat(component2).hasSize(4);
        assertThat(component3).hasSize(1);
        assertThat(component4).hasSize(3);

        assertThat(component1).containsExactlyInAnyOrder(3, 4, 5);
        assertThat(component2).containsExactlyInAnyOrder(6, 7, 8, 9);
        assertThat(component3).containsExactlyInAnyOrder(10);
        assertThat(component4).containsExactlyInAnyOrder(0, 1, 2);
    }

    @Test
    public void getStronglyConnectedComponentsTest2() {
        // Граф показан на рисунке test/resources/test_graph.jpg
        final int[][] A2 = new int[][]{
            {},         // вершина 0
            {2},        // 1
            {3, 12},    // 2
            {6, 4},     // 3
            {5},        // 4
            {4, 8},     // 5
            {7},        // 6
            {8},        // 7
            {9, 6, 11}, // 8
            {10},       // 9
            {},         // 10
            {9, 12},    // 11
            {13},       // 12
            {14},       // 13
            {11},       // 14
        };

        Graph graph = new Graph(A2);

        MyArrayList<MyArrayList<Integer>> stronglyConnectedComponents = graph.getStronglyConnectedComponents();

        assertThat(stronglyConnectedComponents).hasSize(9);

        MyArrayList<Integer> component1 = stronglyConnectedComponents.get(0);
        MyArrayList<Integer> component2 = stronglyConnectedComponents.get(1);
        MyArrayList<Integer> component3 = stronglyConnectedComponents.get(2);
        MyArrayList<Integer> component4 = stronglyConnectedComponents.get(3);
        MyArrayList<Integer> component5 = stronglyConnectedComponents.get(4);
        MyArrayList<Integer> component6 = stronglyConnectedComponents.get(5);
        MyArrayList<Integer> component7 = stronglyConnectedComponents.get(6);
        MyArrayList<Integer> component8 = stronglyConnectedComponents.get(7);
        MyArrayList<Integer> component9 = stronglyConnectedComponents.get(8);

        assertThat(component1).hasSize(1);
        assertThat(component2).hasSize(1);
        assertThat(component3).hasSize(4);
        assertThat(component4).hasSize(3);
        assertThat(component5).hasSize(2);
        assertThat(component6).hasSize(1);
        assertThat(component7).hasSize(1);
        assertThat(component8).hasSize(1);
        assertThat(component9).hasSize(1);

        assertThat(component1).containsExactlyInAnyOrder(10);
        assertThat(component2).containsExactlyInAnyOrder(9);
        assertThat(component3).containsExactlyInAnyOrder(11, 12, 13, 14);
        assertThat(component4).containsExactlyInAnyOrder(6, 7, 8);
        assertThat(component5).containsExactlyInAnyOrder(4, 5);
        assertThat(component6).containsExactlyInAnyOrder(3);
        assertThat(component7).containsExactlyInAnyOrder(2);
        assertThat(component8).containsExactlyInAnyOrder(1);
        assertThat(component9).containsExactlyInAnyOrder(0);
    }

    private Graph createTestGraph() {
        Graph graph = new Graph(10);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(1, 4);
        graph.addEdge(3, 4);

        return graph;
    }
}
