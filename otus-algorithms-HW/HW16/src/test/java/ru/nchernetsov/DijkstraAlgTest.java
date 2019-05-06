package ru.nchernetsov;

import org.junit.Test;
import ru.nchernetsov.graph.Edge;
import ru.nchernetsov.graph.WeightedDirectedGraph;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static ru.nchernetsov.DijkstraAlg.dijkstraAlg;

public class DijkstraAlgTest {

    @Test
    public void dijkstraAlgTest1() {
        WeightedDirectedGraph graph = new WeightedDirectedGraph(6, 9);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(0, 1, 7);
        graph.addEdge(0, 2, 9);
        graph.addEdge(0, 5, 14);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 11);
        graph.addEdge(2, 5, 2);
        graph.addEdge(3, 4, 6);
        graph.addEdge(5, 4, 9);

        Edge[] edgesFrom0to4 = dijkstraAlg(graph, 0, 4);

        assertThat(edgesFrom0to4.length).isEqualTo(3);
        assertThat(edgesFrom0to4).containsExactly(
            new Edge(0, 2, 9), new Edge(2, 5, 2), new Edge(5, 4, 9));
    }
}
