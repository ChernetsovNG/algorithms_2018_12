package ru.nchernetsov;

import org.junit.Test;
import ru.nchernetsov.graph.DirectedGraph;
import ru.nchernetsov.graph.Graph;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.DemucronAlg.demucronTopologySort;
import static ru.nchernetsov.graph.GraphUtils.convertAdjVectorsToAdjMatrix;

public class DemucronAlgTest {

    @Test
    public void demucronTopologySortTest1() {
        Graph graph = createTestGraph();

        int[][] adjMatrix = convertAdjVectorsToAdjMatrix(graph.getVertexCount(), graph.getAdjVectorsList());

        assertThat(adjMatrix[0]).isEqualTo(new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0});
        assertThat(adjMatrix[1]).isEqualTo(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0});
        assertThat(adjMatrix[2]).isEqualTo(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[3]).isEqualTo(new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[4]).isEqualTo(new int[]{0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0});
        assertThat(adjMatrix[5]).isEqualTo(new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0});
        assertThat(adjMatrix[6]).isEqualTo(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0});
        assertThat(adjMatrix[7]).isEqualTo(new int[]{0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[8]).isEqualTo(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        assertThat(adjMatrix[9]).isEqualTo(new int[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0});
        assertThat(adjMatrix[10]).isEqualTo(new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[11]).isEqualTo(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[12]).isEqualTo(new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertThat(adjMatrix[13]).isEqualTo(new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Test
    public void demucronTopologySortTest2() {
        DirectedGraph graph = createTestGraph();

        int[][] result = demucronTopologySort(graph);

        assertThat(result.length).isEqualTo(6);

        assertThat(result[0]).isEqualTo(new int[]{4, 7});
        assertThat(result[1]).isEqualTo(new int[]{1, 8, 9});
        assertThat(result[2]).isEqualTo(new int[]{0, 6, 13});
        assertThat(result[3]).isEqualTo(new int[]{5});
        assertThat(result[4]).isEqualTo(new int[]{3, 10, 11, 12});
        assertThat(result[5]).isEqualTo(new int[]{2});
    }

    private DirectedGraph createTestGraph() {
        DirectedGraph graph = new DirectedGraph(14);

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
        graph.addVertex(11);
        graph.addVertex(12);
        graph.addVertex(13);

        graph.addEdge(0, 2);
        graph.addEdge(0, 12);
        graph.addEdge(1, 12);
        graph.addEdge(1, 12);
        graph.addEdge(3, 2);
        graph.addEdge(4, 2);
        graph.addEdge(4, 8);
        graph.addEdge(4, 9);
        graph.addEdge(5, 3);
        graph.addEdge(5, 10);
        graph.addEdge(5, 11);
        graph.addEdge(5, 12);
        graph.addEdge(6, 10);
        graph.addEdge(7, 1);
        graph.addEdge(7, 3);
        graph.addEdge(7, 5);
        graph.addEdge(7, 6);
        graph.addEdge(8, 0);
        graph.addEdge(8, 13);
        graph.addEdge(9, 0);
        graph.addEdge(9, 6);
        graph.addEdge(9, 11);
        graph.addEdge(10, 2);
        graph.addEdge(12, 2);
        graph.addEdge(13, 5);

        return graph;
    }
}
