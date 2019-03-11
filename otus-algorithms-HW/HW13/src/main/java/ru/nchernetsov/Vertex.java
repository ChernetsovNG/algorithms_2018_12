package ru.nchernetsov;

/**
 * Вершина графа
 */
public class Vertex {

    private final int index;

    /**
     * Посещена ли эта вершина графа при обходе?
     */
    private boolean isVisited = false;

    public Vertex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
