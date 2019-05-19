package ru.nchernetsov.GradientDescent;

import java.util.Arrays;
import java.util.List;

public class DatasetElement {

    private final int xSize;

    private final Double[] vectorX;

    private final Double Y;

    public DatasetElement(int xSize, Double[] vectorX, Double Y) {
        this.xSize = xSize;
        this.vectorX = vectorX;
        this.Y = Y;
    }

    /**
     * Вектор признаков
     */
    public List<Double> getVectorX() {
        return Arrays.asList(vectorX);
    }

    /**
     * Целевое значение
     */
    public Double getY() {
        return Y;
    }
}
