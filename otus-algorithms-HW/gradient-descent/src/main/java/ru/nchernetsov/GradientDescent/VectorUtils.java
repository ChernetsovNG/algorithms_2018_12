package ru.nchernetsov.GradientDescent;

import java.util.Arrays;
import java.util.function.BiFunction;

class VectorUtils {

    /**
     * Поэлементное вычитание двух векторов
     */
    static Double[] substractVectors(Double[] vector1, Double[] vector2) {
        return doOperationWithVectors(vector1, vector2, (x, y) -> x - y);
    }

    /**
     * Поэлементное деление двух векторов
     */
    static Double[] divideVectors(Double[] vector1, Double[] vector2) {
        return doOperationWithVectors(vector1, vector2, (x, y) -> x / y);
    }

    /**
     * Скалярное произведение двух векторов
     */
    public static Double scalarProduct(Double[] vector1, Double[] vector2) {
        Double[] multiplyVectors = doOperationWithVectors(vector1, vector2, (x1, x2) -> x1 * x2);
        return Arrays.stream(multiplyVectors)
            .reduce(0.0, Double::sum);
    }

    /**
     * Умножить все элементы вектора на число
     */
    public static Double[] multiplyVectorOnNumber(Double[] vector, double number) {
        return Arrays.stream(vector)
            .map(element -> element * number)
            .toArray(Double[]::new);
    }

    /**
     * Норма вектора
     */
    public static Double vectorNorm(Double[] vector) {
        double sum = 0.0;
        for (Double elem : vector) {
            sum += elem * elem;
        }
        return Math.sqrt(sum);
    }

    private static Double[] doOperationWithVectors(Double[] vector1, Double[] vector2, BiFunction<Double, Double, Double> operation) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Sizes of vectors are different: vector1 size = "
                + vector1.length + " vector2 size = " + vector2.length);
        }
        int size = vector1.length;

        Double[] result = new Double[size];

        for (int i = 0; i < size; i++) {
            Double vector1I = vector1[i];
            Double vector2I = vector2[i];
            Double resultI = operation.apply(vector1I, vector2I);
            result[i] = resultI;
        }

        return result;
    }
}
