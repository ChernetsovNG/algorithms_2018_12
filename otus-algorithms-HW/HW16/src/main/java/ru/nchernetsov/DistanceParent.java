package ru.nchernetsov;

/**
 * Расстояние до заданной вершины и родительская вершина на кратчайшем пути
 */
public class DistanceParent {

    /**
     * Расстояние от начальной вершины до текущей
     */
    private int distance;

    /**
     * Текущий родитель вершины на кратчайшем пути от начальной вершины до текущей
     */
    private int parentVertex;

    DistanceParent(int distance, int parentVertex) {
        this.distance = distance;
        this.parentVertex = parentVertex;
    }

    int getDistance() {
        return distance;
    }

    int getParentVertex() {
        return parentVertex;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }

    void setParentVertex(int parentVertex) {
        this.parentVertex = parentVertex;
    }

    @Override
    public String toString() {
        return "DistanceParent{" +
            "distance=" + distance +
            ", parentVertex=" + parentVertex +
            '}';
    }
}
