package ru.nchernetsov.graph;

/**
 * Вершина графа в виде точки с координатами
 */
public class VertexPoint2D {

    private final int index;

    private final double x;

    private final double y;

    /**
     * Посещена ли эта вершина графа при обходе?
     */
    private boolean isVisited = false;

    public VertexPoint2D(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
