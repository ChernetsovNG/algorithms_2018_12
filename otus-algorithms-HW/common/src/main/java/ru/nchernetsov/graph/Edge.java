package ru.nchernetsov.graph;

import java.util.Objects;

/**
 * Ребро графа, соединяющее 2 вершины v1 и v2
 */
public class Edge implements Comparable<Edge> {

    private final int v1;

    private final int v2;

    private final Double weight;

    public Edge(int v1, int v2) {
        this(v1, v2, 1);
    }

    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = (double) weight;
    }

    public Edge(int v1, int v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight.compareTo(other.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (v1 != edge.v1) return false;
        if (v2 != edge.v2) return false;
        return Objects.equals(weight, edge.weight);
    }

    @Override
    public int hashCode() {
        int result = v1;
        result = 31 * result + v2;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }
}
