package ru.nchernetsov.GradientDescent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class VectorUtils {

    /**
     * Поэлементное вычитание двух векторов
     */
    static List<Double> substractVectors(List<Double> vector1, List<Double> vector2) {
        return doOperationWithVectors(vector1, vector2, (x, y) -> x - y);
    }

    /**
     * Поэлементное деление двух векторов
     */
    static List<Double> divideVectors(List<Double> vector1, List<Double> vector2) {
        return doOperationWithVectors(vector1, vector2, (x, y) -> x / y);
    }

    /**
     * Скалярное произведение двух векторов
     */
    public static Double scalarProduct(List<Double> vector1, List<Double> vector2) {
        List<Double> multiplyVectors = doOperationWithVectors(vector1, vector2, (x1, x2) -> x1 * x2);
        return multiplyVectors.stream()
            .reduce(0.0, Double::sum);
    }

    /**
     * Умножить все элементы вектора на число
     */
    public static List<Double> multiplyVectorOnNumber(List<Double> vector, double number) {
        return vector.stream()
            .map(element -> element * number)
            .collect(Collectors.toList());
    }

    private static <E> List<E> doOperationWithVectors(List<E> vector1, List<E> vector2, BiFunction<E, E, E> operation) {
        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("Sizes of vectors are different: vector1 size = "
                + vector1.size() + " vector2 size = " + vector2.size());
        }
        int size = vector1.size();

        List<E> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            E vector1I = vector1.get(i);
            E vector2I = vector2.get(i);
            E resultI = operation.apply(vector1I, vector2I);
            result.add(resultI);
        }

        return result;
    }
}
