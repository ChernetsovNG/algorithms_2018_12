package ru.nchernetsov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GraphTest {

    @Test
    public void graphTest1() {
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
        assertThat(adjVectorVertex4).hasSize(0);

        assertThat(adjVectorVertex0).containsExactly(1, 2);
        assertThat(adjVectorVertex1).containsExactly(2, 3, 4);
        assertThat(adjVectorVertex2).containsExactly(3);
        assertThat(adjVectorVertex3).containsExactly(4);

        List<Integer> traversalOrder = new ArrayList<>();
        graph.dfs(0, vertex -> traversalOrder.add(vertex.getIndex()));

        assertThat(traversalOrder).containsExactly(0, 2, 3, 4, 1);
    }
}
