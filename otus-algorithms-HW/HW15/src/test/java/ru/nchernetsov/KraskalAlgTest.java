package ru.nchernetsov;

import org.junit.Test;
import ru.nchernetsov.graph.Edge;
import ru.nchernetsov.graph.WeightedUndirectedGraph;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.KraskalAlg.kraskalMinimalSpanningTree;

public class KraskalAlgTest {

    @Test
    public void kraskalMinimalSpanningTreeTest1() {
        WeightedUndirectedGraph graph = new WeightedUndirectedGraph(7, 11);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);

        graph.addEdge(0, 1, 7);
        graph.addEdge(1, 2, 8);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 9);
        graph.addEdge(1, 4, 7);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 15);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 8);
        graph.addEdge(4, 6, 9);
        graph.addEdge(5, 6, 11);

        Edge[] minimalSpanningTree = kraskalMinimalSpanningTree(graph);

        assertThat(minimalSpanningTree.length).isEqualTo(6);
        assertThat(minimalSpanningTree).containsExactlyInAnyOrder(
            new Edge(0, 1, 7), new Edge(0, 3, 5), new Edge(1, 4, 7),
            new Edge(2, 4, 5), new Edge(3, 5, 6), new Edge(4, 6, 9));
    }
}
