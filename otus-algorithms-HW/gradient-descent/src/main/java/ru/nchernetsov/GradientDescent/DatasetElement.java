package ru.nchernetsov.GradientDescent;

import java.util.Arrays;

public class DatasetElement {

    private final Double[] vectorX;

    private final Double Y;

    DatasetElement(Double[] vectorX, Double Y) {
        this.vectorX = vectorX;
        this.Y = Y;
    }

    /**
     * Вектор признаков
     */
    Double[] getVectorX() {
        return Arrays.copyOf(vectorX, vectorX.length);
    }

    /**
     * Целевое значение
     */
    Double getY() {
        return Y;
    }
}
