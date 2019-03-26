package ru.otus.nchernetsov.graph;

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

    public void markVisited() {
        isVisited = true;
    }

    public void markUnvisited() {
        isVisited = false;
    }
}
