package ru.nchernetsov;

import org.junit.Test;
import ru.nchernetsov.graph.VertexPoint2D;
import ru.nchernetsov.graph.WeightedDirectedGraph2D;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.AStarAlg.aStarAlg;

public class AStarAlgTest {

    @Test
    public void aStarAlgTest1() {
        WeightedDirectedGraph2D graph = new WeightedDirectedGraph2D(8, 11);

        graph.addVertex(0, 0.0, 0.0);
        graph.addVertex(1, 1.0, 3.0);
        graph.addVertex(2, 1.0, 6.0);
        graph.addVertex(3, 3.0, 7.0);
        graph.addVertex(4, 4.0, 6.0);
        graph.addVertex(5, 3.0, 1.0);
        graph.addVertex(6, 5.0, 0.0);
        graph.addVertex(7, 6.0, 4.0);

        graph.addEdge(0, 1);
        graph.addEdge(0, 5);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);
        graph.addEdge(7, 4);
        graph.addEdge(6, 4);

        Path pathFrom0to7 = aStarAlg(graph, 0, 7);
        MyArrayList<VertexPoint2D> pathFrom0to7vertices = pathFrom0to7.getVertices();
        List<Integer> pathFrom0to7verticesIndices = pathFrom0to7vertices.stream()
            .map(VertexPoint2D::getIndex)
            .collect(Collectors.toList());

        assertThat(pathFrom0to7verticesIndices).hasSize(3);
        assertThat(pathFrom0to7verticesIndices).containsExactly(0, 5, 7);
    }

    @Test
    public void aStarAlgTest2() {
        WeightedDirectedGraph2D graph = new WeightedDirectedGraph2D(7, 8);

        graph.addVertex(0, 0.0, 0.0);
        graph.addVertex(1, 2.0, 0.0);
        graph.addVertex(2, 4.0, 0.0);
        graph.addVertex(3, 6.0, 0.0);
        graph.addVertex(4, 2.0, 1.0);
        graph.addVertex(5, 4.0, 1.0);
        graph.addVertex(6, 3.0, -1.0);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(0, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(0, 6);
        graph.addEdge(6, 3);

        Path pathFrom0to3 = aStarAlg(graph, 0, 3);
        MyArrayList<VertexPoint2D> pathFrom0to3vertices = pathFrom0to3.getVertices();
        List<Integer> pathFrom0to3verticesIndices = pathFrom0to3vertices.stream()
            .map(VertexPoint2D::getIndex)
            .collect(Collectors.toList());

        assertThat(pathFrom0to3verticesIndices).hasSize(4);
        assertThat(pathFrom0to3verticesIndices).containsExactly(0, 1, 2, 3);

        Path pathFrom3to0 = aStarAlg(graph, 3, 0);

        assertThat(pathFrom3to0.getVertices()).hasSize(0);
    }
}
