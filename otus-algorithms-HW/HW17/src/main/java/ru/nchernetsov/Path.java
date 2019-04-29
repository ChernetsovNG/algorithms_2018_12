package ru.nchernetsov;

import ru.nchernetsov.graph.VertexPoint2D;

import static ru.nchernetsov.graph.GraphUtils.distance;

/**
 * Путь в графе, состоящий из множества вершин, причём приоритет пути вычислятеся
 * при помощи заданной функции f, равной сумме длины пути и расстояния по прямой до целевой вершины
 */
public class Path implements Comparable<Path> {

    private MyArrayList<VertexPoint2D> vertices = new MyArrayList<>();

    private final VertexPoint2D goal;

    /**
     * Длина пути
     */
    private Double length;

    public Path(VertexPoint2D goal) {
        length = 0.0;
        this.goal = goal;
    }

    public Path(VertexPoint2D vertex, VertexPoint2D goal) {
        this(goal);
        addVertex(vertex);
    }

    public Path(Path otherPath) {
        this(otherPath.getGoal());
        this.length = otherPath.getLength();
        vertices.addAll(otherPath.vertices);
    }

    public void addVertex(VertexPoint2D vertex) {
        if (!vertices.isEmpty()) {
            VertexPoint2D lastVertex = vertices.get(vertices.size() - 1);
            double segmentLength = distance(vertex, lastVertex);
            length += segmentLength;
        }
        vertices.add(vertex);
    }

    public VertexPoint2D lastVertex() {
        if (vertices.isEmpty()) {
            throw new IllegalStateException("Path doesn't contains any vertices");
        }
        return vertices.get(vertices.size() - 1);
    }

    @Override
    public int compareTo(Path other) {
        return this.f().compareTo(other.f());
    }

    private Double f() {
        return g() + h();
    }

    private double g() {
        return length;
    }

    /**
     * Эмпирическая функция h, равная расстоянию по прямой от последней вершины пути до целевой вершины goal
     *
     * @return значение функции h
     */
    private double h() {
        VertexPoint2D lastVertex = lastVertex();
        return distance(lastVertex, goal);
    }

    public MyArrayList<VertexPoint2D> getVertices() {
        return vertices;
    }

    public Double getLength() {
        return length;
    }

    public VertexPoint2D getGoal() {
        return goal;
    }
}
